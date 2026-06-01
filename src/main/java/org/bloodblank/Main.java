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
        primaryStage.setTitle("Donor Darah App");
        // Kita mulai dengan StackPane kosong
        primaryStage.setScene(new Scene(new StackPane(), 800, 600));
        primaryStage.setMaximized(true);
        showLogin();
    }

    public static void showLogin() throws IOException { loadScene("/view/login.fxml"); }
    public static void showRegister() throws IOException { loadScene("/view/register.fxml"); }
    public static void showDashboard() throws IOException { loadScene("/view/dashboard.fxml"); }

    private static void loadScene(String fxmlPath) throws IOException {
        // Menggunakan getResourceAsStream untuk memastikan file terbaca
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
        primaryStage.getScene().setRoot(root);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}