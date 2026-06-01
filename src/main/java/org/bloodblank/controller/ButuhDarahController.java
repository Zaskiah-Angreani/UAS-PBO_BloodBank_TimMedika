package org.bloodblank.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bloodblank.model.Request;

public class ButuhDarahController {

    // Statistik
    @FXML private Label statTotal, statPending, statSetuju, statStatus;

    // Tabel & Kolom
    @FXML private TableView<Request> tabelRiwayat;
    @FXML private TableColumn<Request, String> colID, colPasien, colStatus;

    private int totalCount = 0;
    private ObservableList<Request> listRequest = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        System.out.println("DEBUG: ButuhDarahController diinisialisasi.");

        // Inisialisasi Tabel
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tabelRiwayat.setItems(listRequest);
    }

    // Metode ini wajib ada karena dipanggil oleh tombol "+ Ajukan Permintaan Baru" di FXML Anda
    @FXML
    public void handleTambahBaru() {
        System.out.println("DEBUG: Membuka dialog tambah permintaan...");
        // Untuk saat ini, kita beri alert sederhana.
        // Nanti Anda bisa mengganti ini dengan membuka form input.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Form Input");
        alert.setContentText("Fitur tambah permintaan akan segera hadir!");
        alert.showAndWait();
    }
}