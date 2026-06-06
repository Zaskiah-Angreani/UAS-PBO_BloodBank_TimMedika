package org.bloodblank.model;

public class StokDarah {
    private String golongan;
    private int jumlahStok;
    private String rumahSakit;

    public StokDarah(String golongan, int jumlahStok, String rumahSakit) {
        this.golongan = golongan;
        this.jumlahStok = jumlahStok;
        this.rumahSakit = rumahSakit;
    }

    // Getter & Setter
    public String getGolongan() { return golongan; }
    public int getJumlahStok() { return jumlahStok; }
    public void setJumlahStok(int jumlahStok) { this.jumlahStok = jumlahStok; }
    public String getRumahSakit() { return rumahSakit; }
}