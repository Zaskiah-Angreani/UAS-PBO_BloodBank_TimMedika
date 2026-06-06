package org.bloodblank.model;

import javafx.beans.property.*;

public class Request {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty pasien = new SimpleStringProperty(); // Fixed
    private final StringProperty golDarah = new SimpleStringProperty();
    private final IntegerProperty kantong = new SimpleIntegerProperty();
    private final StringProperty rumahSakit = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty detail = new SimpleStringProperty();

    public Request(String id, String pasien, String golDarah, int kantong, String rumahSakit, String status, String tanggal, String detail) {
        this.id.set(id);
        this.pasien.set(pasien);
        this.golDarah.set(golDarah);
        this.kantong.set(kantong);
        this.rumahSakit.set(rumahSakit);
        this.status.set(status);
        this.tanggal.set(tanggal);
        this.detail.set(detail);
    }

    // Property Getters
    public StringProperty idProperty() { return id; }
    public StringProperty pasienProperty() { return pasien; }
    public StringProperty golDarahProperty() { return golDarah; }
    public IntegerProperty kantongProperty() { return kantong; }
    public StringProperty rumahSakitProperty() { return rumahSakit; }
    public StringProperty statusProperty() { return status; }
    public StringProperty tanggalProperty() { return tanggal; }
    public StringProperty detailProperty() { return detail; }

    // Standard Getters
    public String getId() { return id.get(); }
    public String getPasien() { return pasien.get(); }
    public String getGolDarah() { return golDarah.get(); }
    public int getKantong() { return kantong.get(); }
    public String getRumahSakit() { return rumahSakit.get(); }
    public String getStatus() { return status.get(); }
    public String getTanggal() { return tanggal.get(); }
    public String getDetail() { return detail.get(); }
}