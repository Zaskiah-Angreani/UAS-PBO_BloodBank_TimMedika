package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.bloodblank.model.*;
import org.bloodblank.repository.DataRepository;
import org.bloodblank.Main;
import java.io.IOException;

public class AdminController {
    @FXML private TableView<StokDarah> tabelStok;
    @FXML private TableColumn<StokDarah, String> colGol, colRS;
    @FXML private TableColumn<StokDarah, Integer> colJumlah;

    @FXML private TableView<Request> tabelRequest;
    @FXML private TableColumn<Request, String> colPasien, colStatusRequest;

    @FXML
    public void initialize() {
        // 1. SETUP EDITABLE STOK
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

        // 2. SETUP TABEL REQUEST (Tampilkan semua data tanpa filter)
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colStatusRequest.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Langsung ambil dari repository untuk menampilkan semua riwayat
        tabelRequest.setItems(DataRepository.getListRequest());
    }

    @FXML
    public void handleSetuju() {
        Request req = tabelRequest.getSelectionModel().getSelectedItem();
        // Cek apakah item yang dipilih statusnya masih PENDING agar tidak double klik
        if (req != null && "PENDING".equals(req.getStatus())) {
            req.setStatus("DISETUJUI");
            req.setDetail("Jadwal: " + java.time.LocalDate.now().plusDays(1) + " | RS: " + req.getRumahSakit());
            tabelRequest.refresh();
        } else {
            showWarning("Data sudah diproses!");
        }
    }

    @FXML
    public void handleTolak() {
        Request req = tabelRequest.getSelectionModel().getSelectedItem();
        if (req != null && "PENDING".equals(req.getStatus())) {
            req.setStatus("DITOLAK");
            req.setDetail("Pengajuan ditolak oleh admin.");
            tabelRequest.refresh();
        } else {
            showWarning("Data sudah diproses!");
        }
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.show();
    }

    @FXML
    public void handleLogout() throws IOException {
        Main.showLogin();
    }
}