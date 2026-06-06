package org.bloodblank.controller;

import org.bloodblank.Main;
import org.bloodblank.model.*;
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
        if (username.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Peringatan", "Username dan Password tidak boleh kosong!");
            return;
        }

        // 2. Validasi Kesesuaian Password
        if (pass.equals(confirmPass)) {

            // 3. Membuat Objek User Baru (Penerapan PBO: Membuat instance dari subclass Donor)
            // Anda bisa menambah logika untuk memilih role (Admin/Donor/Penerima) di sini nantinya
            User newUser = new Donor(username, pass, "User Baru", "-");

            // 4. Menyimpan ke Sesi (Singleton)
            // Catatan: Di masa depan, simpan user ini ke DataRepository agar tidak hilang saat aplikasi ditutup
            UserSession.getInstance().setCurrentUser(newUser);

            // 5. Notifikasi Sukses
            showAlert(Alert.AlertType.INFORMATION, "Registrasi Sukses", "Akun berhasil dibuat! Selamat datang, " + username);

            // 6. Kembali ke Login
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