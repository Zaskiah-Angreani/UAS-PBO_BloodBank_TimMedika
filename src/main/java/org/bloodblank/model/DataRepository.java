package org.bloodblank.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * CATATAN PENGEMBANG:
 * Database sementara (In-Memory).
 * Saat database asli (MySQL/SQLite) sudah siap, ubah isi method
 * getListRequest() untuk mengambil data langsung dari database.
 */
public class DataRepository {
    private static final ObservableList<Request> listRequest = FXCollections.observableArrayList();

    public static ObservableList<Request> getListRequest() {
        return listRequest;
    }
}