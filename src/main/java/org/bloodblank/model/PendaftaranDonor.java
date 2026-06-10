package org.bloodblank.model;

import javafx.beans.property.*;

/**
 * Model untuk menyimpan data pendaftaran donor.
 * Merepresentasikan seorang pendonor yang mendaftar untuk suatu permintaan darah.
 */
public class PendaftaranDonor {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty namaDonor = new SimpleStringProperty();
    private final StringProperty golDarah = new SimpleStringProperty();
    private final IntegerProperty beratBadan = new SimpleIntegerProperty();
    private final StringProperty rumahSakit = new SimpleStringProperty();
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty requestId = new SimpleStringProperty(); // ID permintaan yang di-donorkan

    public PendaftaranDonor(String id, String namaDonor, String golDarah, int beratBadan,
                            String rumahSakit, String tanggal, String status, String requestId) {
        this.id.set(id);
        this.namaDonor.set(namaDonor);
        this.golDarah.set(golDarah);
        this.beratBadan.set(beratBadan);
        this.rumahSakit.set(rumahSakit);
        this.tanggal.set(tanggal);
        this.status.set(status);
        this.requestId.set(requestId);
    }

    // Property Getters
    public StringProperty idProperty() { return id; }
    public StringProperty namaDonorProperty() { return namaDonor; }
    public StringProperty golDarahProperty() { return golDarah; }
    public IntegerProperty beratBadanProperty() { return beratBadan; }
    public StringProperty rumahSakitProperty() { return rumahSakit; }
    public StringProperty tanggalProperty() { return tanggal; }
    public StringProperty statusProperty() { return status; }
    public StringProperty requestIdProperty() { return requestId; }

    // Standard Getters
    public String getId() { return id.get(); }
    public String getNamaDonor() { return namaDonor.get(); }
    public String getGolDarah() { return golDarah.get(); }
    public int getBeratBadan() { return beratBadan.get(); }
    public String getRumahSakit() { return rumahSakit.get(); }
    public String getTanggal() { return tanggal.get(); }
    public String getStatus() { return status.get(); }
    public String getRequestId() { return requestId.get(); }

    // Setters
    public void setStatus(String status) { this.status.set(status); }
}
