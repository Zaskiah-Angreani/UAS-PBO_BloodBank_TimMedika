package org.bloodblank.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.IOException;

import org.bloodblank.Main;
import org.bloodblank.model.UserSession;
import org.bloodblank.donordarahapi.entity.User;
import org.bloodblank.donordarahapi.service.UserService;

public class LoginController {

    private final UserService userService =
            Main.getContext().getBean(UserService.class);

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label userErrorLabel;
    @FXML private Label passErrorLabel;

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
        });

        // Tekan Enter di username → pindah fokus ke password
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        // Tekan Enter di password → langsung login
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try { handleLogin(); } catch (IOException e) { e.printStackTrace(); }
            }
        });
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
    public void handleLogin() throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null ||
                password == null ||
                username.length() < 5 ||
                password.length() < 8) {

            showAlert(Alert.AlertType.WARNING, "Peringatan",
                    "Periksa kembali input Anda. Username minimal 5 karakter dan password minimal 8 karakter!");
            return;
        }

        if (username.equals("admin") && password.equals("admin111")) {
            Main.showAdminDashboard();
            return;
        }

        User foundUser = userService.login(username, password);

        if (foundUser != null) {
            UserSession.getInstance().setCurrentUser(foundUser);
            showAlert(Alert.AlertType.INFORMATION, "Sukses",
                    "Login berhasil! Selamat datang, " + foundUser.getNama());
            Main.showDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Gagal",
                    "Username atau password salah!");
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
