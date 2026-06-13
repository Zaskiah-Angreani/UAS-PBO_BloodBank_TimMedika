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

    @FXML private Label userErrorLabel;
    @FXML private Label passErrorLabel;
    @FXML private Label confirmPassErrorLabel;

    @FXML
    public void initialize() {
        // Validasi real-time Username
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                showError(userErrorLabel, "Username tidak boleh kosong");
            } else if (newVal.length() < 5) {
                showError(userErrorLabel, "Username harus minimal 5 karakter");
            } else {
                hideError(userErrorLabel);
            }
        });

        // Validasi real-time Password
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                showError(passErrorLabel, "Password tidak boleh kosong");
            } else if (newVal.length() < 8) {
                showError(passErrorLabel, "Password harus minimal 8 karakter");
            } else {
                hideError(passErrorLabel);
            }
            validateConfirmPassword(); // Re-validasi konfirmasi jika password utama berubah
        });

        // Validasi real-time Konfirmasi Password
        confirmPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            validateConfirmPassword();
        });
    }

    private void validateConfirmPassword() {
        String pass = passwordField.getText();
        String confirmPass = confirmPasswordField.getText();
        
        if (confirmPass.isEmpty()) {
            showError(confirmPassErrorLabel, "Konfirmasi password tidak boleh kosong");
        } else if (!confirmPass.equals(pass)) {
            showError(confirmPassErrorLabel, "Password tidak cocok!");
        } else {
            hideError(confirmPassErrorLabel);
        }
    }

    private void showError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);
    }

    private void hideError(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    @FXML
    public void handleRegister() throws IOException {
        String username = usernameField.getText();
        String pass = passwordField.getText();
        String confirmPass = confirmPasswordField.getText();

        // 1. Validasi Input Kosong & Panjang Karakter
        if (username == null || pass == null || confirmPass == null || username.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Semua kolom wajib diisi!");
            return;
        }
        
        if (username.length() < 5 || pass.length() < 8) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Username minimal 5 karakter dan Password minimal 8 karakter!");
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