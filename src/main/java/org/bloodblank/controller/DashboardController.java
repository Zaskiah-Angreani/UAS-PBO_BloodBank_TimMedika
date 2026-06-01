package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class DashboardController {
    @FXML private Label usernameLabel;
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        System.out.println("DEBUG: DashboardController berhasil diinisialisasi.");
        if (usernameLabel != null) {
            usernameLabel.setText("Halo, " + UserSession.registeredUsername);
        }
    }

    @FXML
    public void handleDonor() {
        System.out.println("DEBUG: Tombol MENDONOR diklik.");
        loadPage("donor.fxml");
    }

    @FXML
    public void handleNeed() {
        System.out.println("DEBUG: Tombol BUTUH DARAH diklik.");
        loadPage("butuh_darah.fxml");
    }

    private void loadPage(String fxml) {
        try {
            // Memastikan path benar: mencari di folder /view/
            String path = "/view/" + fxml;
            System.out.println("DEBUG: Mencoba memuat file: " + path);

            Parent page = FXMLLoader.load(getClass().getResource(path));

            // Mengganti konten
            contentArea.getChildren().setAll(page);
            System.out.println("DEBUG: Halaman " + fxml + " berhasil dimuat.");

        } catch (IOException | NullPointerException e) {
            System.err.println("ERROR: Gagal memuat " + fxml + ". Pastikan file ada di /resources/view/");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() throws IOException {
        System.out.println("DEBUG: Melakukan Logout.");
        UserSession.clearSession();
        Main.showLogin();
    }
}