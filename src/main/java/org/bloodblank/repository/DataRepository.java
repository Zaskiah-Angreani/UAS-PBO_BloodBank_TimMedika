package org.bloodblank.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bloodblank.model.Request;

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