package org.bloodblank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Sistem Manajemen Donor Darah");

        // Inisialisasi awal scene dengan ukuran default
        primaryStage.setScene(new Scene(new StackPane(), 900, 650));
        primaryStage.setMaximized(true);

        // Memulai aplikasi dari halaman Login
        showLogin();
    }

    // --- Navigation Methods ---

    public static void showLogin() throws IOException {
        loadScene("/view/login.fxml");
    }

    public static void showRegister() throws IOException {
        loadScene("/view/register.fxml");
    }

    public static void showDashboard() throws IOException {
        loadScene("/view/dashboard.fxml");
    }

    public static void showAdminDashboard() throws IOException {
        loadScene("/view/admin_dashboard.fxml");
    }

    // --- Helper Method ---

    private static void loadScene(String fxmlPath) throws IOException {
        // Menggunakan resource loading yang standar
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Parent root = loader.load();

        // Mengganti root dari scene yang sudah ada (efisien untuk ganti halaman)
        if (primaryStage.getScene() == null) {
            primaryStage.setScene(new Scene(root));
        } else {
            primaryStage.getScene().setRoot(root);
        }
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}