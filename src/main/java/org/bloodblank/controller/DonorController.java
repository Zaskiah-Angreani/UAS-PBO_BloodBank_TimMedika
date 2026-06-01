package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DonorController {
    @FXML private ComboBox<String> bloodTypeBox;
    @FXML private TextField weightField;

    @FXML
    public void initialize() {
        // Mengisi pilihan golongan darah
        bloodTypeBox.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    }

    @FXML
    public void handleRegisterDonor() {
        String blood = bloodTypeBox.getValue();
        String weight = weightField.getText();

        if (blood == null || weight.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Data Belum Lengkap", "Harap isi semua kolom!");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Berhasil", "Data donor Anda telah tersimpan!");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}