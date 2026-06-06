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
            // Mengambil input dan mengubah menjadi kapital (dengan trim untuk menghapus spasi di awal/akhir)
            String golDarahInput = inputGolDarah.getText().trim().toUpperCase();
            String rsInput = inputRS.getText().trim().toUpperCase();
            String statusInput = "PENDING"; // Dibuat kapital agar konsisten

            // Membuat objek Request baru
            Request newReq = new Request(
                    "REQ" + (System.currentTimeMillis() % 1000),
                    inputPasien.getText(), // Nama pasien biarkan apa adanya atau sesuai preferensi
                    golDarahInput,
                    Integer.parseInt(inputKantong.getText()),
                    rsInput,        // Data RS menjadi KAPITAL
                    statusInput,    // Data Status menjadi KAPITAL
                    java.time.LocalDate.now().toString(),
                    "Belum ada detail"
            );

            // Menambah langsung ke sumber data utama (Repository)
            DataRepository.getListRequest().add(newReq);

            // Menutup form setelah data berhasil disimpan
            ((Stage) inputPasien.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            // Memberikan notifikasi jika input kantong bukan angka
            Alert alert = new Alert(Alert.AlertType.ERROR, "Jumlah kantong harus berupa angka!");
            alert.setHeaderText("Input Tidak Valid");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleBatal() {
        ((Stage) inputPasien.getScene().getWindow()).close();
    }
}