package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.*;
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

        // Memanfaatkan Polymorphism: mengambil info user yang sedang login
        User currentUser = UserSession.getInstance().getCurrentUser();
        if (usernameLabel != null && currentUser != null) {
            usernameLabel.setText("Halo, " + currentUser.getNama());
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
            String path = "/view/" + fxml;
            FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource(path));
            Parent page = loader.load();
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            System.err.println("ERROR: Gagal memuat file FXML: " + fxml);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() throws IOException {
        System.out.println("DEBUG: Melakukan Logout.");
        UserSession.getInstance().clearSession(); // Menggunakan instance
        Main.showLogin();
    }
}