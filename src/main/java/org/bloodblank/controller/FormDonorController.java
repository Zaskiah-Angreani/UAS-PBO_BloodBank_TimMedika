package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bloodblank.model.*;
import org.bloodblank.repository.DataRepository;

/**
 * Controller untuk form pendaftaran donor darah.
 * Menangani validasi kelayakan donor dan penyimpanan data pendaftaran.
 */
public class FormDonorController {

    @FXML private Label labelPasien, labelGolDarah, labelRS, labelKantong;
    @FXML private TextField inputNamaDonor, inputBeratBadan, inputUsia;
    @FXML private ComboBox<String> comboGolDarah;
    @FXML private CheckBox checkRiwayat;

    private Request currentRequest;

    @FXML
    public void initialize() {
        // Mengisi pilihan golongan darah
        comboGolDarah.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

        // Pre-fill nama donor dari sesi user saat ini
        User currentUser = UserSession.getInstance().getCurrentUser();
        if (currentUser != null) {
            inputNamaDonor.setText(currentUser.getNama());
            // Jika user adalah Donor, pre-fill golongan darahnya
            if (currentUser instanceof Donor) {
                String gol = ((Donor) currentUser).getGolonganDarah();
                if (gol != null && !gol.equals("-")) {
                    comboGolDarah.setValue(gol);
                }
            }
        }
    }

    /**
     * Menerima data permintaan darah yang dipilih dari tabel utama.
     */
    public void setRequest(Request req) {
        this.currentRequest = req;
        labelPasien.setText(req.getPasien());
        labelGolDarah.setText(req.getGolDarah());
        labelRS.setText(req.getRumahSakit());
        labelKantong.setText(req.getKantong() + " kantong");
    }

    @FXML
    public void handleDaftar() {
        // --- Validasi Input ---
        String namaDonor = inputNamaDonor.getText().trim();
        String golDarah = comboGolDarah.getValue();
        String beratStr = inputBeratBadan.getText().trim();
        String usiaStr = inputUsia.getText().trim();

        if (namaDonor.isEmpty() || golDarah == null || beratStr.isEmpty() || usiaStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Data Belum Lengkap", "Harap isi semua kolom yang diperlukan!");
            return;
        }

        int berat;
        int usia;
        try {
            berat = Integer.parseInt(beratStr);
            usia = Integer.parseInt(usiaStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Tidak Valid", "Berat badan dan usia harus berupa angka!");
            return;
        }

        // --- Validasi Kelayakan Donor ---
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

        // --- Validasi Kecocokan Golongan Darah ---
        if (!golDarah.equalsIgnoreCase(currentRequest.getGolDarah())) {
            showAlert(Alert.AlertType.WARNING, "Golongan Darah Tidak Cocok",
                    "Golongan darah Anda (" + golDarah + ") tidak cocok dengan kebutuhan (" + currentRequest.getGolDarah() + ").");
            return;
        }

        // --- Simpan Pendaftaran Donor ---
        PendaftaranDonor donorBaru = new PendaftaranDonor(
                "DON" + (System.currentTimeMillis() % 10000),
                namaDonor,
                golDarah,
                berat,
                currentRequest.getRumahSakit(),
                java.time.LocalDate.now().toString(),
                "MENUNGGU",
                currentRequest.getId()
        );

        DataRepository.getListDonor().add(donorBaru);

        showAlert(Alert.AlertType.INFORMATION, "Pendaftaran Berhasil",
                "Terima kasih, " + namaDonor + "!\n\n" +
                        "Data pendaftaran donor Anda telah tersimpan.\n" +
                        "Golongan Darah: " + golDarah + "\n" +
                        "Untuk Pasien: " + currentRequest.getPasien() + "\n" +
                        "Rumah Sakit: " + currentRequest.getRumahSakit() + "\n\n" +
                        "Silakan datang ke rumah sakit sesuai jadwal yang ditentukan.");

        // Tutup dialog
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
