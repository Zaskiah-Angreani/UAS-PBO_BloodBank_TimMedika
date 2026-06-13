package org.bloodblank.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bloodblank.model.*;
import org.bloodblank.repository.DataRepository;

import java.io.IOException;

public class DonorController {

    // --- Statistik Labels ---
    @FXML private Label statTotalDonor, statMenunggu, statDiterima, statSukarela;

    // --- Tabel Permintaan Darah yang Tersedia ---
    @FXML private TableView<Request> tabelPermintaan;
    @FXML private TableColumn<Request, String> colIdReq, colPasienReq, colGolReq, colRsReq, colTanggalReq, colStatusReq;
    @FXML private TableColumn<Request, Integer> colKantongReq;
    @FXML private TableColumn<Request, Void> colAksiReq;

    // --- Tabel Riwayat Pendaftaran Donor ---
    @FXML private TableView<PendaftaranDonor> tabelRiwayatDonor;
    @FXML private TableColumn<PendaftaranDonor, String> colIdDonor, colNamaDonor, colGolDonor, colRsDonor, colTglDonor, colStatusDonor;
    @FXML private TableColumn<PendaftaranDonor, Integer> colBeratDonor;

    private ObservableList<Request> listRequest = DataRepository.getListRequest();
    private ObservableList<PendaftaranDonor> listDonor = DataRepository.getListDonor();

    @FXML
    public void initialize() {
        // --- Setup Tabel Permintaan Darah yang Tersedia ---
        setupColumn(colIdReq, "id");
        setupColumn(colPasienReq, "pasien");
        setupColumn(colGolReq, "golDarah");
        setupColumn(colKantongReq, "kantong");
        setupColumn(colRsReq, "rumahSakit");
        setupColumn(colTanggalReq, "tanggal");
        setupColumn(colStatusReq, "status");

        // Tombol "Daftar Donor" di setiap baris permintaan
        colAksiReq.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Daftar Donor");
            {
                btn.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand; -fx-padding: 5 10;");
                btn.setOnAction(e -> {
                    Request req = getTableView().getItems().get(getIndex());
                    handleDaftarDonor(req);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Request req = getTableView().getItems().get(getIndex());
                    // Hanya tampilkan tombol jika status permintaan PENDING (belum ada donor)
                    if ("PENDING".equals(req.getStatus())) {
                        setGraphic(btn);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        // Filter hanya menampilkan permintaan yang statusnya PENDING atau DISETUJUI
        tabelPermintaan.setItems(listRequest);

        // --- Setup Tabel Riwayat Donor ---
        setupColumn(colIdDonor, "id");
        setupColumn(colNamaDonor, "namaDonor");
        setupColumn(colGolDonor, "golDarah");
        setupColumn(colBeratDonor, "beratBadan");
        setupColumn(colRsDonor, "rumahSakit");
        setupColumn(colTglDonor, "tanggal");
        setupColumn(colStatusDonor, "status");

        tabelRiwayatDonor.setItems(listDonor);

        // Update statistik awal
        updateStatistik();

        // Listener untuk update otomatis ketika data berubah
        listDonor.addListener((javafx.collections.ListChangeListener<PendaftaranDonor>) c -> {
            updateStatistik();
            tabelRiwayatDonor.refresh();
        });

        listRequest.addListener((javafx.collections.ListChangeListener<Request>) c -> {
            tabelPermintaan.refresh();
        });
    }

    /**
     * Menangani aksi pendaftaran donor untuk permintaan darah tertentu.
     * Membuka dialog form pendaftaran donor.
     */
    private void handleDaftarDonor(Request req) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_donor.fxml"));
            Parent root = loader.load();

            // Kirim data permintaan ke controller form
            FormDonorController formController = loader.getController();
            formController.setRequest(req);

            Stage stage = new Stage();
            stage.setTitle("Formulir Pendaftaran Donor");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh setelah dialog ditutup
            tabelPermintaan.refresh();
            tabelRiwayatDonor.refresh();
            updateStatistik();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menangani klik tombol "Daftarkan Diri Sebagai Pendonor" (donor sukarela).
     * Membuka dialog form pendaftaran donor sukarela tanpa memerlukan permintaan darah.
     */
    @FXML
    public void handleDaftarSukarela() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_donor_sukarela.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Formulir Pendaftaran Donor Sukarela");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMinWidth(500);
            stage.setMinHeight(620);
            stage.setResizable(true);
            stage.setScene(new Scene(root, 500, 650));
            stage.showAndWait();

            // Refresh setelah dialog ditutup
            tabelRiwayatDonor.refresh();
            updateStatistik();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menangani klik tombol golongan darah dari halaman donor
     */
    @FXML
    public void handleLihatStok(javafx.event.ActionEvent event) {
        Button btn = (Button) event.getSource();
        String golDarah = btn.getText().replace("GOL ", "").trim();

        StokDarah stok = cariStok(golDarah);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info Stok " + golDarah);
        alert.setHeaderText("Informasi Stok Darah");

        if (stok != null) {
            alert.setContentText("Rumah Sakit: " + stok.getRumahSakit() +
                    "\nStok Tersedia: " + stok.getJumlahStok() + " kantong");
        } else {
            alert.setContentText("Data stok untuk golongan darah " + golDarah + " tidak ditemukan.");
        }
        alert.show();
    }

    private StokDarah cariStok(String golongan) {
        for (StokDarah s : DataRepository.getListStok()) {
            if (s.getGolongan().equalsIgnoreCase(golongan)) {
                return s;
            }
        }
        return null;
    }

    private <T> void setupColumn(TableColumn<?, T> col, String property) {
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setStyle("-fx-alignment: CENTER_LEFT;");
    }

    private void updateStatistik() {
        long total = listDonor.size();
        long menunggu = listDonor.stream().filter(d -> "MENUNGGU".equals(d.getStatus())).count();
        long diterima = listDonor.stream().filter(d -> "DITERIMA".equals(d.getStatus())).count();
        long sukarela = listDonor.stream().filter(d -> "SUKARELA".equals(d.getRequestId())).count();

        statTotalDonor.setText(String.valueOf(total));
        statMenunggu.setText(String.valueOf(menunggu));
        statDiterima.setText(String.valueOf(diterima));
        statSukarela.setText(String.valueOf(sukarela));
    }
}