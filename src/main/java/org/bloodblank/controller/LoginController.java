package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.*;
import org.bloodblank.repository.DataRepository;
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

        // 1. Cek Admin
        if (user.equals("admin") && pass.equals("admin111")) {
            Main.showAdminDashboard();
            return;
        }

        // 2. Cek User di Repository
        User foundUser = null;
        for (User u : DataRepository.getListUser()) {
            if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                foundUser = u;
                break;
            }
        }

        if (foundUser != null) {
            UserSession.getInstance().setCurrentUser(foundUser);
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Login Berhasil! Selamat datang, " + foundUser.getNama());
            Main.showDashboard();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau Password salah atau belum terdaftar!");
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