package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bloodblank.Main;
import org.bloodblank.donordarahapi.entity.Donor;
import org.bloodblank.donordarahapi.service.DonorService;
import org.bloodblank.model.*;
import org.bloodblank.donordarahapi.repository.DataRepository;

public class FormDonorController {

    @FXML private Label labelPasien, labelGolDarah, labelRS, labelKantong;
    @FXML private TextField inputNamaDonor, inputBeratBadan, inputUsia;
    @FXML private ComboBox<String> comboGolDarah;
    @FXML private CheckBox checkRiwayat;

    private final DonorService donorService =
            Main.getContext().getBean(DonorService.class);

    private Request currentRequest;

    @FXML
    public void initialize() {
        comboGolDarah.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

        org.bloodblank.donordarahapi.entity.User currentUser =
                UserSession.getInstance().getCurrentUser();

        if (currentUser != null) {
            inputNamaDonor.setText(currentUser.getNama());
        }
    }

    public void setRequest(Request req) {
        this.currentRequest = req;
        labelPasien.setText(req.getPasien());
        labelGolDarah.setText(req.getGolDarah());
        labelRS.setText(req.getRumahSakit());
        labelKantong.setText(req.getKantong() + " kantong");
    }

    @FXML
    public void handleDaftar() {
        String namaDonor = inputNamaDonor.getText().trim();
        String golDarah = comboGolDarah.getValue();
        String beratStr = inputBeratBadan.getText().trim();
        String usiaStr = inputUsia.getText().trim();

        if (namaDonor.isEmpty() || golDarah == null || beratStr.isEmpty() || usiaStr.isEmpty()) {
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
        if (!golDarah.equalsIgnoreCase(currentRequest.getGolDarah())) {
            showAlert(Alert.AlertType.WARNING, "Golongan Darah Tidak Cocok",
                    "Golongan darah Anda (" + golDarah + ") tidak cocok dengan kebutuhan ("
                            + currentRequest.getGolDarah() + ").");
            return;
        }

        // Simpan ke DataRepository (untuk tampilan JavaFX)
        PendaftaranDonor donorBaru = new PendaftaranDonor(
                "DON" + (System.currentTimeMillis() % 10000),
                namaDonor, golDarah, berat,
                currentRequest.getRumahSakit(),
                java.time.LocalDate.now().toString(),
                "MENUNGGU",
                currentRequest.getId()
        );
        DataRepository.getListDonor().add(donorBaru);

        // Simpan ke database H2
        Donor donorEntity = new Donor();
        donorEntity.setNama(namaDonor);
        donorEntity.setGolDarah(golDarah);
        donorEntity.setBeratBadan(berat);
        donorEntity.setStatus("MENUNGGU");
        donorService.save(donorEntity);

        showAlert(Alert.AlertType.INFORMATION, "Pendaftaran Berhasil",
                "Terima kasih, " + namaDonor + "!\n\n" +
                        "Data pendaftaran donor Anda telah tersimpan.\n" +
                        "Golongan Darah: " + golDarah + "\n" +
                        "Untuk Pasien: " + currentRequest.getPasien() + "\n" +
                        "Rumah Sakit: " + currentRequest.getRumahSakit() + "\n\n" +
                        "Silakan datang ke rumah sakit sesuai jadwal yang ditentukan.");

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
