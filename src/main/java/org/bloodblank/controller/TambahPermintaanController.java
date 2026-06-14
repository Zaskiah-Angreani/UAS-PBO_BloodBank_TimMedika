package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bloodblank.Main;
import org.bloodblank.donordarahapi.service.RequestService;
import org.bloodblank.model.Request;
import org.bloodblank.model.StokDarah;
import org.bloodblank.repository.DataRepository;

public class TambahPermintaanController {

    @FXML private TextField inputPasien, inputGolDarah, inputKantong, inputRS;

    private final RequestService requestService =
            Main.getContext().getBean(RequestService.class);

    @FXML
    public void handleSimpan() {
        try {
            String golDarahInput = inputGolDarah.getText().trim().toUpperCase();
            String rsInput = inputRS.getText().trim().toUpperCase();

            if (inputKantong.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Kosong",
                        "Jumlah kantong wajib diisi!");
                return;
            }

            int jumlahKantong = Integer.parseInt(inputKantong.getText());

            StokDarah stokTarget = null;
            boolean rsValid = false;

            for (StokDarah stok : DataRepository.getListStok()) {
                if (stok.getGolongan().equalsIgnoreCase(golDarahInput)) {
                    if (stok.getRumahSakit().equalsIgnoreCase(rsInput)) {
                        stokTarget = stok;
                        rsValid = true;
                        break;
                    }
                }
            }

            if (!rsValid) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Golongan darah atau Rumah Sakit tidak tersedia/tidak cocok!");
                return;
            }

            if (stokTarget.getJumlahStok() < jumlahKantong) {
                showAlert(Alert.AlertType.WARNING, "Stok Kurang",
                        "Stok hanya tersedia: " + stokTarget.getJumlahStok() + " kantong.");
                return;
            }

            // Kurangi stok
            stokTarget.setJumlahStok(stokTarget.getJumlahStok() - jumlahKantong);

            String tanggal = java.time.LocalDate.now().toString();
            String idReq = "REQ" + (System.currentTimeMillis() % 1000);
            String detail = "Tersedia di: " + rsInput;

            // Simpan ke DataRepository (untuk tampilan JavaFX)
            Request newReq = new Request(
                    idReq,
                    inputPasien.getText().trim(),
                    golDarahInput,
                    jumlahKantong,
                    rsInput,
                    "PENDING",
                    tanggal,
                    detail
            );
            DataRepository.getListRequest().add(newReq);

            // Simpan ke database H2
            org.bloodblank.donordarahapi.entity.Request reqEntity =
                    new org.bloodblank.donordarahapi.entity.Request();
            reqEntity.setPasien(inputPasien.getText().trim());
            reqEntity.setGolDarah(golDarahInput);
            reqEntity.setKantong(jumlahKantong);
            reqEntity.setRumahSakit(rsInput);
            reqEntity.setStatus("PENDING");
            reqEntity.setTanggal(tanggal);
            reqEntity.setDetail(detail);
            requestService.save(reqEntity);

            showAlert(Alert.AlertType.INFORMATION, "Sukses",
                    "Permintaan berhasil diajukan dan stok telah diperbarui.");

            ((Stage) inputPasien.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Tidak Valid",
                    "Jumlah kantong harus berupa angka!");
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
