package org.bloodblank.model;

public class Request {
    private String id;
    private String pasien;
    private String golDarah;
    private int kantong;
    private String rs;
    private String status;

    // Constructor untuk membuat objek baru
    public Request(String id, String pasien, String golDarah, int kantong, String rs, String status) {
        this.id = id;
        this.pasien = pasien;
        this.golDarah = golDarah;
        this.kantong = kantong;
        this.rs = rs;
        this.status = status;
    }

    // Getter (diperlukan oleh TableView untuk menampilkan data)
    public String getId() { return id; }
    public String getPasien() { return pasien; }
    public String getGolDarah() { return golDarah; }
    public int getKantong() { return kantong; }
    public String getRs() { return rs; }
    public String getStatus() { return status; }
}