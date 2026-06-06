package org.bloodblank.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bloodblank.model.Request;
import org.bloodblank.model.StokDarah;
import org.bloodblank.model.User;

public class DataRepository {
    private static final ObservableList<Request> listRequest = FXCollections.observableArrayList();
    private static final ObservableList<StokDarah> listStok = FXCollections.observableArrayList();
    private static final ObservableList<User> listUser = FXCollections.observableArrayList();

    static {
        listStok.add(new StokDarah("O+", 50, "RSUD Dr. Pirngadi"));
        listStok.add(new StokDarah("O-", 30, "RS USU"));
        listStok.add(new StokDarah("A+", 45, "RSUP H. Adam Malik"));
        listStok.add(new StokDarah("A-", 20, "RSUD Dr. Pirngadi"));
        listStok.add(new StokDarah("B+", 40, "RS USU"));
        listStok.add(new StokDarah("B-", 15, "RSUP H. Adam Malik"));
        listStok.add(new StokDarah("AB+", 35, "RSUD Dr. Pirngadi"));
        listStok.add(new StokDarah("AB-", 10, "RS USU"));
    }

    public static ObservableList<Request> getListRequest() { return listRequest; }
    public static ObservableList<StokDarah> getListStok() { return listStok; }
    public static ObservableList<User> getListUser() { return listUser; }
}