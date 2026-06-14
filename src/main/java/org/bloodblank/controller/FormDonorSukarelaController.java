package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bloodblank.Main;
import org.bloodblank.donordarahapi.entity.Donor;
import org.bloodblank.donordarahapi.service.DonorService;
import org.bloodblank.model.*;
import org.bloodblank.donordarahapi.repository.DataRepository;

public class FormDonorSukarelaController {

    @FXML private TextField inputNamaDonor, inputBeratBadan, inputUsia;
    @FXML private ComboBox<String> comboGolDarah, comboRumahSakit;
    @FXML private CheckBox checkRiwayat;

    private final DonorService donorService =
            Main.getContext().getBean(DonorService.class);

    @FXML
    public void initialize() {
        comboGolDarah.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        comboRumahSakit.getItems().addAll("RSUD Dr. Pirngadi", "RS USU", "RSUP H. Adam Malik");

        org.bloodblank.donordarahapi.entity.User currentUser =
                UserSession.getInstance().getCurrentUser();

        if (currentUser != null) {
            inputNamaDonor.setText(currentUser.getNama());
        }
    }

    @FXML
    public void handleDaftar() {
        String namaDonor = inputNamaDonor.getText().trim();
        String golDarah = comboGolDarah.getValue();
        String beratStr = inputBeratBadan.getText().trim();
        String usiaStr = inputUsia.getText().trim();
        String rumahSakit = comboRumahSakit.getValue();

        if (namaDonor.isEmpty() || golDarah == null || beratStr.isEmpty()
                || usiaStr.isEmpty() || rumahSakit == null) {
            showAlert(Alert.AlertType.WARNING, "Data Belum Lengkap",
                    "Harap isi semua kolom yang diperlukan!");
            return;
        }

        int berat, usia;
        try {
            berat = Integer.parseInt(beratStr);
            usia = Integer.parseInt(usiaStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Tidak Valid",
                    "Berat badan dan usia harus berupa angka!");
            return;
        }

        if (berat < 45) {
            showAlert(Alert.AlertType.WARNING, "Berat Badan Kurang",
                    "Berat badan minimal untuk donor darah adalah 45 kg.\nBerat Anda: " + berat + " kg.");
            return;
        }
        if (usia < 17 || usia > 65) {
            showAlert(Alert.AlertType.WARNING, "Usia Tidak Memenuhi Syarat",
                    "Usia donor harus antara 17 - 65 tahun.\nUsia Anda: " + usia + " tahun.");
            return;
        }
        if (checkRiwayat.isSelected()) {
            showAlert(Alert.AlertType.WARNING, "Tidak Memenuhi Syarat",
                    "Maaf, Anda tidak memenuhi syarat donor karena memiliki riwayat penyakit menular.");
            return;
        }

        // Simpan ke DataRepository (untuk tampilan JavaFX)
        PendaftaranDonor donorBaru = new PendaftaranDonor(
                "DON" + (System.currentTimeMillis() % 10000),
                namaDonor, golDarah, berat, rumahSakit,
                java.time.LocalDate.now().toString(),
                "TERSEDIA", "SUKARELA"
        );
        DataRepository.getListDonor().add(donorBaru);

        // Simpan ke database H2
        Donor donorEntity = new Donor();
        donorEntity.setNama(namaDonor);
        donorEntity.setGolDarah(golDarah);
        donorEntity.setBeratBadan(berat);
        donorEntity.setStatus("TERSEDIA");
        donorService.save(donorEntity);

        showAlert(Alert.AlertType.INFORMATION, "Pendaftaran Berhasil",
                "Terima kasih, " + namaDonor + "!\n\n" +
                        "Anda telah terdaftar sebagai pendonor sukarela.\n" +
                        "Golongan Darah: " + golDarah + "\n" +
                        "Lokasi Donor: " + rumahSakit + "\n\n" +
                        "Data Anda akan tersedia bagi yang membutuhkan.");

        ((Stage) inputNamaDonor.getScene().getWindow()).close();
    }

    @FXML
    public void handleBatal() {
        ((Stage) inputNamaDonor.getScene().getWindow()).close();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
