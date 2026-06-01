package org.bloodblank.model;

public class Request {
    private String id;
    private String pasien;
    private String golDarah;
    private int kantong;
    private String rumahSakit; // Ubah dari 'rs' agar lebih jelas
    private String status;
    private String tanggal;    // Tambahkan field ini

    public Request(String id, String pasien, String golDarah, int kantong, String rumahSakit, String status, String tanggal) {
        this.id = id;
        this.pasien = pasien;
        this.golDarah = golDarah;
        this.kantong = kantong;
        this.rumahSakit = rumahSakit;
        this.status = status;
        this.tanggal = tanggal;
    }

    // Getter yang disesuaikan agar cocok dengan Controller
    public String getId() { return id; }
    public String getPasien() { return pasien; }
    public String getGolDarah() { return golDarah; }
    public int getKantong() { return kantong; }
    public String getRumahSakit() { return rumahSakit; } // Cocok dengan "rumahSakit"
    public String getStatus() { return status; }
    public String getTanggal() { return tanggal; }      // Cocok dengan "tanggal"
}