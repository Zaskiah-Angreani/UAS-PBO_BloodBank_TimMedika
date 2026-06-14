package org.bloodblank.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bloodblank.model.Request;
import org.bloodblank.model.StokDarah;
import org.bloodblank.donordarahapi.repository.DataRepository;
import java.io.IOException;

public class ButuhDarahController {

    @FXML private Label statTotal, statPending, statSetuju, statStatus;
    @FXML private TableView<Request> tabelRiwayat;

    @FXML private TableColumn<Request, String> colID, colPasien, colGolDarah, colRS, colTanggal, colStatus;
    @FXML private TableColumn<Request, Integer> colKantong;
    @FXML private TableColumn<Request, Void> colDetail;

    private ObservableList<Request> listRequest = DataRepository.getListRequest();

    @FXML
    public void initialize() {
        setupColumn(colID, "id");
        setupColumn(colPasien, "pasien");
        setupColumn(colGolDarah, "golDarah");
        setupColumn(colKantong, "kantong");
        setupColumn(colRS, "rumahSakit");
        setupColumn(colTanggal, "tanggal");
        setupColumn(colStatus, "status");

        colDetail.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Lihat");
            {
                btn.setOnAction(e -> {
                    Request req = getTableView().getItems().get(getIndex());
                    if ("DISETUJUI".equals(req.getStatus())) {
                        tampilkanDetail(req);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Permintaan masih PENDING!");
                        alert.show();
                    }
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tabelRiwayat.setItems(listRequest);
        updateStatistik();

        listRequest.addListener((javafx.collections.ListChangeListener<Request>) c -> {
            updateStatistik();
            tabelRiwayat.refresh();
        });
    }

    // --- FITUR BARU: Menampilkan stok darah saat tombol golongan diklik ---
    @FXML
    public void handleLihatStok(ActionEvent event) {
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
    // ------------------------------------------------------------------------

    private void setupColumn(TableColumn<Request, ?> col, String property) {
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setStyle("-fx-alignment: CENTER_LEFT;");
    }

    private void tampilkanDetail(Request req) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detail Pengambilan");
        alert.setHeaderText("Instruksi Pengambilan Darah");
        alert.setContentText("Nama Pasien: " + req.getPasien() +
                "\nRumah Sakit: " + req.getRumahSakit() +
                "\nDetail: " + req.getDetail());
        alert.show();
    }

    private void updateStatistik() {
        statTotal.setText(String.valueOf(listRequest.size()));
        statPending.setText(String.valueOf(listRequest.stream().filter(r -> "PENDING".equals(r.getStatus())).count()));
        statSetuju.setText(String.valueOf(listRequest.stream().filter(r -> "DISETUJUI".equals(r.getStatus())).count()));
        statStatus.setText("SISTEM AKTIF");
    }

    @FXML
    public void handleTambahBaru() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tambah_permintaan.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Tambah Permintaan");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}