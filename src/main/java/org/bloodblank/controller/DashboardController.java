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
            // Gunakan path absolut dari folder resources
            String path = "/view/" + fxml;
            System.out.println("DEBUG: Mencoba memuat: " + path);

            // Menggunakan DashboardController.class agar path benar-benar dicari dari root
            FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource(path));
            Parent page = loader.load();

            // Mengganti konten
            contentArea.getChildren().setAll(page);
            System.out.println("DEBUG: Halaman " + fxml + " berhasil dimuat.");

        } catch (IOException e) {
            System.err.println("ERROR: Gagal memuat file FXML!");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("ERROR: File tidak ditemukan di path: /view/" + fxml);
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