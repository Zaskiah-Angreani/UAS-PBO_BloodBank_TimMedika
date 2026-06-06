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

        // LOGIKA VALIDASI BARU
        // Karena variabel 'registeredUsername' sudah dihapus,
        // kita melakukan simulasi login (nanti diganti dengan pengecekan ke Repository)

        if (!user.isEmpty() && !pass.isEmpty()) {

            // 1. Buat user sebagai contoh (Nanti ini diambil dari daftar user terdaftar)
            User authenticatedUser = new Donor(user, pass, "User Terdaftar", "O+");

            // 2. Simpan ke Sesi (Singleton)
            UserSession.getInstance().setCurrentUser(authenticatedUser);

            // 3. Notifikasi Sukses
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Login Berhasil! Selamat datang, " + authenticatedUser.getNama());

            // 4. Pindah ke Dashboard
            Main.showDashboard();
        } else {
            // Notifikasi Gagal
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