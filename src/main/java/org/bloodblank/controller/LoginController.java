package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.UserSession;
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

        // Validasi Login
        if (user.equals(UserSession.registeredUsername) && pass.equals(UserSession.registeredPassword)
                && !user.isEmpty()) {

            // Notifikasi Sukses
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Login Berhasil! Selamat datang, " + user);

            // Pindah ke Dashboard
            Main.showDashboard();
        } else {
            // Notifikasi Gagal
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau Password salah!");
        }
    }

    @FXML
    public void handleGoToRegister() throws IOException {
        Main.showRegister();
    }

    // Helper method untuk mempermudah menampilkan Alert
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}