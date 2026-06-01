package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bloodblank.model.Request;
import org.bloodblank.model.DataRepository;

public class TambahPermintaanController {
    @FXML private TextField inputPasien, inputGolDarah, inputKantong, inputRS;

    @FXML
    public void handleSimpan() {
        try {
            Request newReq = new Request(
                    "REQ" + (System.currentTimeMillis() % 1000),
                    inputPasien.getText(),
                    inputGolDarah.getText(),
                    Integer.parseInt(inputKantong.getText()),
                    inputRS.getText(),
                    "Pending",
                    java.time.LocalDate.now().toString(),
                    "Belum ada detail"
            );

            // Langsung akses Repository utama agar tabel otomatis terupdate
            DataRepository.getListRequest().add(newReq);

            ((Stage) inputPasien.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Jumlah kantong harus berupa angka!");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleBatal() {
        ((Stage) inputPasien.getScene().getWindow()).close();
    }
}