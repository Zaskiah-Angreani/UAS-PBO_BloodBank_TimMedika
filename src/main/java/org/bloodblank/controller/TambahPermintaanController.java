package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bloodblank.model.Request;

public class TambahPermintaanController {
    @FXML private TextField inputPasien, inputGolDarah, inputKantong, inputRS;

    // Variabel untuk menyimpan referensi controller utama
    private ButuhDarahController mainController;

    public void setMainController(ButuhDarahController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void handleSimpan() {
        // Buat objek Request baru
        Request newReq = new Request(
                "REQ" + System.currentTimeMillis() % 1000, // ID sederhana
                inputPasien.getText(),
                inputGolDarah.getText(),
                Integer.parseInt(inputKantong.getText()),
                inputRS.getText(),
                "Pending", // Status default
                java.time.LocalDate.now().toString()
        );

        // Kirim data ke controller utama
        if (mainController != null) {
            mainController.tambahkanRequest(newReq);
        }

        ((Stage) inputPasien.getScene().getWindow()).close();
    }

    @FXML public void handleBatal() { ((Stage) inputPasien.getScene().getWindow()).close(); }
}