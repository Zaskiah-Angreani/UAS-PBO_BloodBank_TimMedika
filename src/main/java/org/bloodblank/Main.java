package org.bloodblank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.bloodblank.donordarahapi.DonorDarahApiApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    private static ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = SpringApplication.run(DonorDarahApiApplication.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Sistem Manajemen Donor Darah");

        primaryStage.setScene(
                new Scene(new StackPane(), 900, 650)
        );

        primaryStage.setMaximized(true);

        showLogin();
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

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

    private static void loadScene(String fxmlPath)
            throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        Main.class.getResource(fxmlPath)
                );

        Parent root = loader.load();

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