package org.bloodblank.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.bloodblank.model.*;
import org.bloodblank.repository.DataRepository;
import org.bloodblank.Main;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    // --- Statistik ---
    @FXML private Label statTotalRequest, statTotalDonor, statDonorMenunggu, statDonorDisetujui;

    // --- Tabel Stok Darah ---
    @FXML private TableView<StokDarah> tabelStok;
    @FXML private TableColumn<StokDarah, String> colGol, colRS;
    @FXML private TableColumn<StokDarah, Integer> colJumlah;

    // --- Tabel Request ---
    @FXML private TableView<Request> tabelRequest;
    @FXML private TableColumn<Request, String> colIdReq, colPasien, colGolReq, colRsReq, colStatusRequest;
    @FXML private TableColumn<Request, Integer> colKantongReq;

    // --- Tabel Pendonor ---
    @FXML private TableView<PendaftaranDonor> tabelDonor;
    @FXML private TableColumn<PendaftaranDonor, String> colIdDonor, colNamaDonor, colGolDonor,
            colRsDonor, colTglDonor, colStatusDonor, colRequestDonor;
    @FXML private TableColumn<PendaftaranDonor, Integer> colBeratDonor;
    @FXML private Label labelInfoDonor;

    private final ObservableList<Request> listRequest = DataRepository.getListRequest();
    private final ObservableList<PendaftaranDonor> listDonor = DataRepository.getListDonor();

    @FXML
    public void initialize() {
        // ═══ SETUP TABEL STOK (Editable) ═══
        tabelStok.setEditable(true);
        colJumlah.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colJumlah.setOnEditCommit(event -> {
            StokDarah data = event.getRowValue();
            data.setJumlahStok(event.getNewValue());
        });

        colGol.setCellValueFactory(new PropertyValueFactory<>("golongan"));
        colRS.setCellValueFactory(new PropertyValueFactory<>("rumahSakit"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlahStok"));
        tabelStok.setItems(DataRepository.getListStok());

        // ═══ SETUP TABEL REQUEST ═══
        setupColumn(colIdReq, "id");
        setupColumn(colPasien, "pasien");
        setupColumn(colGolReq, "golDarah");
        setupColumn(colKantongReq, "kantong");
        setupColumn(colRsReq, "rumahSakit");
        setupColumn(colStatusRequest, "status");
        tabelRequest.setItems(listRequest);

        // ═══ SETUP TABEL DONOR ═══
        setupColumn(colIdDonor, "id");
        setupColumn(colNamaDonor, "namaDonor");
        setupColumn(colGolDonor, "golDarah");
        setupColumn(colBeratDonor, "beratBadan");
        setupColumn(colRsDonor, "rumahSakit");
        setupColumn(colTglDonor, "tanggal");
        setupColumn(colStatusDonor, "status");
        setupColumn(colRequestDonor, "requestId");
        tabelDonor.setItems(listDonor);

        // ═══ LISTENERS UNTUK AUTO-REFRESH STATISTIK ═══
        listRequest.addListener((javafx.collections.ListChangeListener<Request>) c -> {
            updateStatistik();
            tabelRequest.refresh();
        });
        listDonor.addListener((javafx.collections.ListChangeListener<PendaftaranDonor>) c -> {
            updateStatistik();
            tabelDonor.refresh();
        });

        updateStatistik();
    }

    // ═══════════════════════════════════════
    //  AKSI: VERIFIKASI PENGAJUAN DARAH
    // ═══════════════════════════════════════

    @FXML
    public void handleSetuju() {
        Request req = tabelRequest.getSelectionModel().getSelectedItem();
        if (req == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Data",
                    "Silakan pilih permintaan dari tabel terlebih dahulu.");
            return;
        }
        if (!"PENDING".equals(req.getStatus())) {
            showAlert(Alert.AlertType.WARNING, "Sudah Diproses",
                    "Permintaan ini sudah berstatus: " + req.getStatus());
            return;
        }

        req.setStatus("DISETUJUI");
        req.setDetail("Jadwal: " + java.time.LocalDate.now().plusDays(1) + " | RS: " + req.getRumahSakit());
        tabelRequest.refresh();
        updateStatistik();

        showAlert(Alert.AlertType.INFORMATION, "Permintaan Disetujui",
                "Permintaan " + req.getId() + " dari pasien " + req.getPasien()
                        + " telah DISETUJUI.\n\nJadwal pengambilan: "
                        + java.time.LocalDate.now().plusDays(1)
                        + "\nRumah Sakit: " + req.getRumahSakit());
    }

    @FXML
    public void handleTolak() {
        Request req = tabelRequest.getSelectionModel().getSelectedItem();
        if (req == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Data",
                    "Silakan pilih permintaan dari tabel terlebih dahulu.");
            return;
        }
        if (!"PENDING".equals(req.getStatus())) {
            showAlert(Alert.AlertType.WARNING, "Sudah Diproses",
                    "Permintaan ini sudah berstatus: " + req.getStatus());
            return;
        }

        req.setStatus("DITOLAK");
        req.setDetail("Pengajuan ditolak oleh admin.");
        tabelRequest.refresh();
        updateStatistik();

        showAlert(Alert.AlertType.INFORMATION, "Permintaan Ditolak",
                "Permintaan " + req.getId() + " dari pasien " + req.getPasien() + " telah DITOLAK.");
    }

    // ═══════════════════════════════════════
    //  AKSI: KELOLA PENDONOR SUKARELA
    // ═══════════════════════════════════════

    /**
     * Menyetujui pendonor sukarela yang statusnya masih MENUNGGU atau TERSEDIA.
     * Donor yang disetujui siap untuk dicocokkan dengan permintaan darah.
     */
    @FXML
    public void handleSetujuiDonor() {
        PendaftaranDonor donor = tabelDonor.getSelectionModel().getSelectedItem();
        if (donor == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Data",
                    "Silakan pilih pendonor dari tabel terlebih dahulu.");
            return;
        }

        String status = donor.getStatus();
        if ("DITERIMA".equals(status) || "DITOLAK".equals(status)) {
            showAlert(Alert.AlertType.WARNING, "Sudah Diproses",
                    "Pendonor ini sudah berstatus: " + status);
            return;
        }

        donor.setStatus("DITERIMA");
        tabelDonor.refresh();
        updateStatistik();

        showAlert(Alert.AlertType.INFORMATION, "Donor Disetujui",
                "Pendonor " + donor.getNamaDonor() + " (Gol. " + donor.getGolDarah()
                        + ") telah DISETUJUI.\n\nRS Tujuan: " + donor.getRumahSakit());
    }

    /**
     * Menolak pendonor sukarela.
     */
    @FXML
    public void handleTolakDonor() {
        PendaftaranDonor donor = tabelDonor.getSelectionModel().getSelectedItem();
        if (donor == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Data",
                    "Silakan pilih pendonor dari tabel terlebih dahulu.");
            return;
        }

        String status = donor.getStatus();
        if ("DITERIMA".equals(status) || "DITOLAK".equals(status)) {
            showAlert(Alert.AlertType.WARNING, "Sudah Diproses",
                    "Pendonor ini sudah berstatus: " + status);
            return;
        }

        donor.setStatus("DITOLAK");
        tabelDonor.refresh();
        updateStatistik();

        showAlert(Alert.AlertType.INFORMATION, "Donor Ditolak",
                "Pendonor " + donor.getNamaDonor() + " telah DITOLAK.");
    }

    /**
     * Pasangkan pendonor sukarela ke permintaan darah yang cocok.
     * Admin memilih donor → sistem menampilkan daftar permintaan yang golongan darahnya cocok
     * → Admin pilih permintaan → Donor di-assign ke permintaan tersebut.
     */
    @FXML
    public void handlePasangkanDonor() {
        PendaftaranDonor donor = tabelDonor.getSelectionModel().getSelectedItem();
        if (donor == null) {
            showAlert(Alert.AlertType.WARNING, "Pilih Data",
                    "Silakan pilih pendonor dari tabel terlebih dahulu.");
            return;
        }

        // Hanya donor sukarela (requestId = SUKARELA) atau yang statusnya TERSEDIA/MENUNGGU bisa dipasangkan
        if (!"SUKARELA".equals(donor.getRequestId()) && !"TERSEDIA".equals(donor.getStatus())) {
            if ("DITERIMA".equals(donor.getStatus())) {
                showAlert(Alert.AlertType.INFORMATION, "Sudah Dipasangkan",
                        "Donor ini sudah disetujui/dipasangkan.");
                return;
            }
        }

        // Cari permintaan yang golongan darahnya cocok dan statusnya PENDING
        List<Request> cocok = listRequest.stream()
                .filter(r -> "PENDING".equals(r.getStatus()))
                .filter(r -> r.getGolDarah().equalsIgnoreCase(donor.getGolDarah()))
                .collect(Collectors.toList());

        if (cocok.isEmpty()) {
            // Jika tidak ada yang cocok persis, tampilkan semua PENDING
            List<Request> semuaPending = listRequest.stream()
                    .filter(r -> "PENDING".equals(r.getStatus()))
                    .collect(Collectors.toList());

            if (semuaPending.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Tidak Ada Permintaan",
                        "Saat ini tidak ada permintaan darah yang berstatus PENDING.");
                return;
            }

            // Tampilkan dialog pilihan dengan info tidak ada yang cocok
            showPasangkanDialog(donor, semuaPending,
                    "⚠ Tidak ada permintaan dengan golongan darah " + donor.getGolDarah()
                            + " yang PENDING.\nBerikut semua permintaan PENDING yang tersedia:");
        } else {
            showPasangkanDialog(donor, cocok,
                    "✅ Ditemukan " + cocok.size() + " permintaan yang cocok (Gol. " + donor.getGolDarah() + "):");
        }
    }

    /**
     * Menampilkan dialog untuk memilih permintaan yang akan dipasangkan dengan donor.
     */
    private void showPasangkanDialog(PendaftaranDonor donor, List<Request> daftarRequest, String infoText) {
        Dialog<Request> dialog = new Dialog<>();
        dialog.setTitle("Pasangkan Donor ke Permintaan");
        dialog.setHeaderText("🔗 Pasangkan " + donor.getNamaDonor() + " (Gol. " + donor.getGolDarah() + ")");

        DialogPane dp = dialog.getDialogPane();
        dp.setStyle("-fx-background-color: #1a1a2e;");
        dp.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        dp.setPrefWidth(500);
        dp.setPrefHeight(400);

        javafx.scene.layout.VBox content = new javafx.scene.layout.VBox(10);
        content.setStyle("-fx-padding: 10;");

        Label info = new Label(infoText);
        info.setStyle("-fx-text-fill: #b3b3b3; -fx-font-size: 12px;");
        info.setWrapText(true);

        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Request r : daftarRequest) {
            items.add(r.getId() + " | Pasien: " + r.getPasien()
                    + " | Gol: " + r.getGolDarah()
                    + " | " + r.getKantong() + " kantong"
                    + " | RS: " + r.getRumahSakit());
        }
        listView.setItems(items);
        listView.setPrefHeight(200);
        listView.setStyle("-fx-background-color: #232946; -fx-control-inner-background: #232946;");

        content.getChildren().addAll(info, listView);
        dp.setContent(content);

        ButtonType pasangkanType = new ButtonType("Pasangkan", ButtonBar.ButtonData.OK_DONE);
        ButtonType batalType = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dp.getButtonTypes().addAll(pasangkanType, batalType);

        // Styling tombol
        Button pasangkanBtn = (Button) dp.lookupButton(pasangkanType);
        pasangkanBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 8; -fx-cursor: hand; -fx-padding: 8 20;");
        Button batalBtn = (Button) dp.lookupButton(batalType);
        batalBtn.setStyle("-fx-background-color: #555; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 8; -fx-cursor: hand; -fx-padding: 8 20;");

        dialog.setResultConverter(buttonType -> {
            if (buttonType == pasangkanType) {
                int idx = listView.getSelectionModel().getSelectedIndex();
                if (idx >= 0 && idx < daftarRequest.size()) {
                    return daftarRequest.get(idx);
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(selectedRequest -> {
            // Update donor: status → DITERIMA, requestId → ID permintaan
            donor.setStatus("DITERIMA");
            donor.setRequestId(selectedRequest.getId());

            // Update request: status → DISETUJUI
            selectedRequest.setStatus("DISETUJUI");
            selectedRequest.setDetail("Donor: " + donor.getNamaDonor()
                    + " | Gol: " + donor.getGolDarah()
                    + " | Jadwal: " + java.time.LocalDate.now().plusDays(1));

            tabelDonor.refresh();
            tabelRequest.refresh();
            updateStatistik();

            showAlert(Alert.AlertType.INFORMATION, "Berhasil Dipasangkan!",
                    "Pendonor " + donor.getNamaDonor() + " berhasil dipasangkan dengan:\n\n"
                            + "Permintaan: " + selectedRequest.getId() + "\n"
                            + "Pasien: " + selectedRequest.getPasien() + "\n"
                            + "Gol. Darah: " + selectedRequest.getGolDarah() + "\n"
                            + "RS: " + selectedRequest.getRumahSakit() + "\n\n"
                            + "Jadwal donor: " + java.time.LocalDate.now().plusDays(1));
        });
    }

    // ═══════════════════════════════════════
    //  HELPER METHODS
    // ═══════════════════════════════════════

    private void updateStatistik() {
        long totalReq = listRequest.size();
        long totalDonor = listDonor.size();
        long donorMenunggu = listDonor.stream()
                .filter(d -> "MENUNGGU".equals(d.getStatus()) || "TERSEDIA".equals(d.getStatus()))
                .count();
        long donorDisetujui = listDonor.stream()
                .filter(d -> "DITERIMA".equals(d.getStatus()))
                .count();

        statTotalRequest.setText(String.valueOf(totalReq));
        statTotalDonor.setText(String.valueOf(totalDonor));
        statDonorMenunggu.setText(String.valueOf(donorMenunggu));
        statDonorDisetujui.setText(String.valueOf(donorDisetujui));

        // Update info label
        long sukarela = listDonor.stream().filter(d -> "SUKARELA".equals(d.getRequestId())).count();
        labelInfoDonor.setText("Donor sukarela: " + sukarela + " | Total terdaftar: " + totalDonor);
    }

    private <T> void setupColumn(TableColumn<?, T> col, String property) {
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setStyle("-fx-alignment: CENTER_LEFT;");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Gunakan custom Label agar warna teks bisa di-kontrol langsung
        Label contentLabel = new Label(content);
        contentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(400);

        DialogPane dp = alert.getDialogPane();
        dp.setStyle("-fx-background-color: #1a1a2e;");
        dp.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        dp.setContent(contentLabel);

        alert.showAndWait();
    }

    @FXML
    public void handleLogout() throws IOException {
        Main.showLogin();
    }
}