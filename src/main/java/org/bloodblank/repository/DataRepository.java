package org.bloodblank.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bloodblank.model.Request;
import org.bloodblank.model.StokDarah;

public class DataRepository {
    // List untuk Request Permintaan Darah
    private static final ObservableList<Request> listRequest = FXCollections.observableArrayList();

    // List untuk Stok Darah (8 Golongan)
    private static final ObservableList<StokDarah> listStok = FXCollections.observableArrayList();

    static {
        // Inisialisasi awal Stok Darah
        listStok.add(new StokDarah("O+", 50, "RSUD Dr. Pirngadi"));
        listStok.add(new StokDarah("O-", 30, "RS USU"));
        listStok.add(new StokDarah("A+", 45, "RSUP H. Adam Malik"));
        listStok.add(new StokDarah("A-", 20, "RSUD Dr. Pirngadi"));
        listStok.add(new StokDarah("B+", 40, "RS USU"));
        listStok.add(new StokDarah("B-", 15, "RSUP H. Adam Malik"));
        listStok.add(new StokDarah("AB+", 35, "RSUD Dr. Pirngadi"));
        listStok.add(new StokDarah("AB-", 10, "RS USU"));
    }

    // Method Getter
    public static ObservableList<Request> getListRequest() {
        return listRequest;
    }

    public static ObservableList<StokDarah> getListStok() {
        return listStok;
    }
}