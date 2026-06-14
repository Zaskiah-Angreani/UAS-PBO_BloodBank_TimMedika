package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.donordarahapi.entity.User;
import org.bloodblank.model.UserSession;
import org.bloodblank.model.StokDarah;
import org.bloodblank.repository.DataRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class DashboardController {
    @FXML private Label usernameLabel;
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        org.bloodblank.donordarahapi.entity.User currentUser =
                UserSession.getInstance().getCurrentUser();

        if (usernameLabel != null && currentUser != null) {
            usernameLabel.setText("Halo, " + currentUser.getNama());
        }
    }

    // Menangani klik tombol golongan darah dari dashboard
    @FXML
    public void handleLihatStok(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String golDarah = btn.getText().replace("GOL ", "").trim();

        StokDarah stok = cariStok(golDarah);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info Stok " + golDarah);
        alert.setHeaderText("Informasi Stok Darah");

        if (stok != null) {
            alert.setContentText("Rumah Sakit: " + stok.getRumahSakit() + "\nStok Tersedia: " + stok.getJumlahStok() + " kantong");
        } else {
            alert.setContentText("Data stok untuk golongan darah " + golDarah + " tidak ditemukan.");
        }
        alert.show();
    }

    // Helper untuk mencari stok di DataRepository
    private StokDarah cariStok(String golongan) {
        for (StokDarah s : DataRepository.getListStok()) {
            if (s.getGolongan().equalsIgnoreCase(golongan)) {
                return s;
            }
        }
        return null;
    }

    @FXML
    public void handleDonor() {
        loadPage("donor.fxml");
    }

    @FXML
    public void handleNeed() {
        loadPage("butuh_darah.fxml");
    }

    private void loadPage(String fxml) {
        try {
            String path = "/view/" + fxml;
            FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource(path));
            Parent page = loader.load();
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() throws IOException {
        UserSession.getInstance().clearSession();
        Main.showLogin();
    }
}
