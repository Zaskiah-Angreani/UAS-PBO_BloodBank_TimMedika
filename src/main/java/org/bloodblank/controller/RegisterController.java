package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.IOException;

import org.bloodblank.Main;
import org.bloodblank.donordarahapi.entity.User;
import org.bloodblank.donordarahapi.service.UserService;

public class RegisterController {

    private final UserService userService =
            Main.getContext().getBean(UserService.class);

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label userErrorLabel;
    @FXML private Label passErrorLabel;
    @FXML private Label confirmPassErrorLabel;

    @FXML
    public void initialize() {

        usernameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                showError(userErrorLabel, "Username tidak boleh kosong");
            } else if (newVal.length() < 5) {
                showError(userErrorLabel, "Username harus minimal 5 karakter");
            } else {
                hideError(userErrorLabel);
            }
        });

        passwordField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                showError(passErrorLabel, "Password tidak boleh kosong");
            } else if (newVal.length() < 8) {
                showError(passErrorLabel, "Password harus minimal 8 karakter");
            } else {
                hideError(passErrorLabel);
            }
            validateConfirmPassword();
        });

        confirmPasswordField.textProperty().addListener((obs, oldVal, newVal) -> {
            validateConfirmPassword();
        });

        // Tekan Enter di username → pindah ke password
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        // Tekan Enter di password → pindah ke konfirmasi password
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirmPasswordField.requestFocus();
            }
        });

        // Tekan Enter di konfirmasi password → langsung register
        confirmPasswordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try { handleRegister(); } catch (IOException e) { e.printStackTrace(); }
            }
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

        if (username == null || pass == null || confirmPass == null ||
                username.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Semua kolom wajib diisi!");
            return;
        }

        if (username.length() < 5 || pass.length() < 8) {
            showAlert(Alert.AlertType.WARNING, "Peringatan",
                    "Username minimal 5 karakter dan Password minimal 8 karakter!");
            return;
        }

        if (!pass.equals(confirmPass)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password tidak cocok!");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(pass);
        newUser.setNama("User Baru");
        newUser.setRole("DONOR");

        userService.save(newUser);

        showAlert(Alert.AlertType.INFORMATION, "Sukses", "Registrasi berhasil! Silakan login.");
        Main.showLogin();
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
