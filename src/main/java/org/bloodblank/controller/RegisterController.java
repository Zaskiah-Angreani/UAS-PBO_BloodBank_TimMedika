package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.*;
import org.bloodblank.repository.DataRepository;
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

        // 1. Validasi Input Kosong
        if (username.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Semua kolom wajib diisi!");
            return;
        }

        // 2. Validasi Kesesuaian Password
        if (pass.equals(confirmPass)) {
            // Membuat akun baru dengan nilai default untuk nama dan golongan darah
            User newUser = new Donor(username, pass, "User Baru", "-");
            DataRepository.getListUser().add(newUser);

            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Registrasi berhasil! Silakan login.");
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