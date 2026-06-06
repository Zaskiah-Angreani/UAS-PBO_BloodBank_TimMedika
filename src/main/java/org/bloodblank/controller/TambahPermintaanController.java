package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bloodblank.model.Request;
import org.bloodblank.model.StokDarah;
import org.bloodblank.repository.DataRepository;

public class TambahPermintaanController {
    @FXML private TextField inputPasien, inputGolDarah, inputKantong, inputRS;

    @FXML
    public void handleSimpan() {
        try {
            String golDarahInput = inputGolDarah.getText().trim().toUpperCase();
            String rsInput = inputRS.getText().trim().toUpperCase();
            int jumlahKantong = Integer.parseInt(inputKantong.getText());

            // 1. Logika Validasi & Pengurangan Stok (PBO: Business Logic)
            StokDarah stokTarget = null;
            for (StokDarah stok : DataRepository.getListStok()) {
                if (stok.getGolongan().equalsIgnoreCase(golDarahInput)) {
                    stokTarget = stok;
                    break;
                }
            }

            if (stokTarget == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Golongan darah tidak ditemukan!");
                return;
            }

            if (stokTarget.getJumlahStok() < jumlahKantong) {
                showAlert(Alert.AlertType.WARNING, "Stok Kurang", "Stok hanya tersedia: " + stokTarget.getJumlahStok());
                return;
            }

            // 2. Kurangi stok setelah validasi lulus
            stokTarget.setJumlahStok(stokTarget.getJumlahStok() - jumlahKantong);

            // 3. Simpan Permintaan Baru
            Request newReq = new Request(
                    "REQ" + (System.currentTimeMillis() % 1000),
                    inputPasien.getText(),
                    golDarahInput,
                    jumlahKantong,
                    rsInput,
                    "PENDING",
                    java.time.LocalDate.now().toString(),
                    "Rumah Sakit: " + rsInput
            );

            DataRepository.getListRequest().add(newReq);

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Permintaan berhasil diajukan & stok diperbarui!");
            ((Stage) inputPasien.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Tidak Valid", "Jumlah kantong harus berupa angka!");
        }
    }

    @FXML
    public void handleBatal() {
        ((Stage) inputPasien.getScene().getWindow()).close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}