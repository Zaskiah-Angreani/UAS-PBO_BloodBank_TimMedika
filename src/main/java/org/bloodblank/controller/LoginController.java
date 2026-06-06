package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void handleLogin() throws IOException {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // 1. Login Admin Khusus
        if (user.equals("admin") && pass.equals("admin111")) {
            Main.showAdminDashboard();
            return;
        }

        // 2. Logika Login User Biasa
        if (!user.isEmpty() && !pass.isEmpty()) {
            // Simulasi user terdaftar
            User authenticatedUser = new Donor(user, pass, "User Terdaftar", "O+");
            UserSession.getInstance().setCurrentUser(authenticatedUser);

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Login Berhasil! Selamat datang, " + authenticatedUser.getNama());
            Main.showDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau Password tidak boleh kosong!");
        }
    }

    @FXML
    public void handleGoToRegister() throws IOException {
        Main.showRegister();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}