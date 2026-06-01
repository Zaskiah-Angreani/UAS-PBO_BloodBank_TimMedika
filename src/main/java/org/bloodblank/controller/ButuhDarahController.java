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
import org.bloodblank.model.Request;
import java.io.IOException;

public class ButuhDarahController {

    @FXML private Label statTotal, statPending, statSetuju, statStatus;
    @FXML private TableView<Request> tabelRiwayat;
    @FXML private TableColumn<Request, String> colID, colGolDarah, colKantong, colRS, colTanggal;

    private ObservableList<Request> listRequest = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGolDarah.setCellValueFactory(new PropertyValueFactory<>("golDarah"));
        colKantong.setCellValueFactory(new PropertyValueFactory<>("kantong"));
        colRS.setCellValueFactory(new PropertyValueFactory<>("rumahSakit"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));

        tabelRiwayat.setItems(listRequest);
    }

    // Metode untuk menerima data dari form tambah
    public void tambahkanRequest(Request req) {
        listRequest.add(req);
        updateStatistik();
    }

    // Metode untuk memperbarui tampilan statistik di atas
    private void updateStatistik() {
        int total = listRequest.size();
        long pending = listRequest.stream().filter(r -> r.getStatus().equals("Pending")).count();
        long setuju = listRequest.stream().filter(r -> r.getStatus().equals("Disetujui")).count();

        statTotal.setText(String.valueOf(total));
        statPending.setText(String.valueOf(pending));
        statSetuju.setText(String.valueOf(setuju));
    }

    @FXML
    public void handleTambahBaru() {
        try {
            // 1. Tentukan path file
            String fxmlPath = "/view/tambah_permintaan.fxml";
            java.net.URL url = getClass().getResource(fxmlPath);

            // 2. Validasi apakah file ditemukan
            if (url == null) {
                System.err.println("ERROR: File FXML tidak ditemukan di: " + fxmlPath);
                return; // Berhenti jika file tidak ada agar tidak crash
            }

            // 3. Load menggunakan URL yang sudah divalidasi
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            TambahPermintaanController controller = loader.getController();
            controller.setMainController(this);

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