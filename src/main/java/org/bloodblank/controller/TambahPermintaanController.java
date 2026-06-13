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
            // Mengambil input dan normalisasi
            String golDarahInput = inputGolDarah.getText().trim().toUpperCase();
            String rsInput = inputRS.getText().trim().toUpperCase();

            // Validasi input angka
            if (inputKantong.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Kosong", "Jumlah kantong wajib diisi!");
                return;
            }
            int jumlahKantong = Integer.parseInt(inputKantong.getText());

            // 1. Logika Validasi: Pastikan Golongan Darah DAN Rumah Sakit ada di database
            StokDarah stokTarget = null;
            boolean rsValid = false;

            for (StokDarah stok : DataRepository.getListStok()) {
                // Cek golongan darah terlebih dahulu
                if (stok.getGolongan().equalsIgnoreCase(golDarahInput)) {
                    // Cek apakah RS ini menyediakan golongan darah tersebut
                    if (stok.getRumahSakit().equalsIgnoreCase(rsInput)) {
                        stokTarget = stok;
                        rsValid = true;
                        break;
                    }
                }
            }

            // 2. Handling Error Validasi
            if (!rsValid) {
                showAlert(Alert.AlertType.ERROR, "Error", "Golongan darah atau Rumah Sakit tidak tersedia/tidak cocok!");
                return;
            }

            if (stokTarget.getJumlahStok() < jumlahKantong) {
                showAlert(Alert.AlertType.WARNING, "Stok Kurang", "Stok hanya tersedia: " + stokTarget.getJumlahStok() + " kantong.");
                return;
            }

            // 3. Eksekusi: Kurangi Stok
            stokTarget.setJumlahStok(stokTarget.getJumlahStok() - jumlahKantong);

            // 4. Simpan Request Baru
            Request newReq = new Request(
                    "REQ" + (System.currentTimeMillis() % 1000),
                    inputPasien.getText().trim(),
                    golDarahInput,
                    jumlahKantong,
                    rsInput, // Sudah dalam format UPPERCASE
                    "PENDING",
                    java.time.LocalDate.now().toString(),
                    "Tersedia di: " + rsInput
            );

            DataRepository.getListRequest().add(newReq);

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Permintaan berhasil diajukan dan stok telah diperbarui.");
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