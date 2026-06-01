package org.bloodblank.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bloodblank.model.Request;
import org.bloodblank.model.DataRepository;
import java.io.IOException;

public class ButuhDarahController {

    @FXML private Label statTotal, statPending, statSetuju;
    @FXML private TableView<Request> tabelRiwayat;

    @FXML private TableColumn<Request, String> colID, colGolDarah, colRS, colTanggal, colStatus;
    @FXML private TableColumn<Request, Integer> colKantong;
    @FXML private TableColumn<Request, Void> colDetail;

    private ObservableList<Request> listRequest = DataRepository.getListRequest();

    @FXML
    public void initialize() {
        // MENGGUNAKAN PROPERTY VALUE FACTORY (Standard JavaFX untuk POJO)
        // Ini akan mencari getter di Request.java (getId, getGolDarah, dll)
        setupColumn(colID, "id");
        setupColumn(colGolDarah, "golDarah");
        setupColumn(colKantong, "kantong");
        setupColumn(colRS, "rumahSakit");
        setupColumn(colTanggal, "tanggal");
        setupColumn(colStatus, "status");

        // Membuat tombol di kolom detail
        colDetail.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Lihat");
            {
                btn.setOnAction(e -> {
                    Request req = getTableView().getItems().get(getIndex());
                    if ("Disetujui".equals(req.getStatus())) {
                        tampilkanDetail(req);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Permintaan masih Pending!");
                        alert.show();
                    }
                });
            }
            @Override protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        tabelRiwayat.setItems(listRequest);
        updateStatistik();

        // Listener untuk update otomatis
        listRequest.addListener((javafx.collections.ListChangeListener<Request>) c -> {
            updateStatistik();
            tabelRiwayat.refresh();
        });
    }

    private void setupColumn(TableColumn<Request, ?> col, String property) {
        // PropertyValueFactory membaca data dari method get[Property] di kelas Request
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setStyle("-fx-alignment: CENTER_LEFT;");
    }

    private void tampilkanDetail(Request req) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detail Pengambilan");
        alert.setHeaderText("Instruksi Pengambilan Darah");
        alert.setContentText("Lokasi Rumah Sakit: " + req.getRumahSakit() +
                "\nDetail: " + req.getDetail());
        alert.show();
    }

    private void updateStatistik() {
        statTotal.setText(String.valueOf(listRequest.size()));
        statPending.setText(String.valueOf(listRequest.stream().filter(r -> "Pending".equals(r.getStatus())).count()));
        statSetuju.setText(String.valueOf(listRequest.stream().filter(r -> "Disetujui".equals(r.getStatus())).count()));
    }

    public void tambahkanRequest(Request req) {
        listRequest.add(req);
    }

    @FXML
    public void handleTambahBaru() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tambah_permintaan.fxml"));
            Parent root = loader.load();
            TambahPermintaanController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Tambah Permintaan");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}