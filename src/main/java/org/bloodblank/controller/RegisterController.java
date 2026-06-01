package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML
    public void handleRegister() throws IOException {
        String username = usernameField.getText();
        String pass = passwordField.getText();
        String confirmPass = confirmPasswordField.getText();

        // Validasi sederhana
        if (username.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Username dan Password tidak boleh kosong!");
            return;
        }

        if (pass.equals(confirmPass)) {
            UserSession.registeredUsername = username;
            UserSession.registeredPassword = pass;

            // Notifikasi Sukses Registrasi
            showAlert(Alert.AlertType.INFORMATION, "Registrasi Sukses", "Akun berhasil dibuat! Silakan login.");

            Main.showLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Password tidak cocok!");
        }
    }

    @FXML
    public void handleBackToLogin() throws IOException {
        Main.showLogin();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}