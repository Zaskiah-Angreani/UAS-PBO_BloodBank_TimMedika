package org.bloodblank.model;

public class Penerima extends User {
    private String riwayatPenyakit;

    public Penerima(String username, String password, String nama, String riwayatPenyakit) {
        super(username, password, nama);
        this.riwayatPenyakit = riwayatPenyakit;
    }

    public String getRiwayatPenyakit() { return riwayatPenyakit; }
    public void setRiwayatPenyakit(String riwayatPenyakit) { this.riwayatPenyakit = riwayatPenyakit; }

    @Override
    public String getRole() { return "PENERIMA"; }

    @Override
    public String getDisplayInfo() { return "Penerima: " + getNama(); }
}