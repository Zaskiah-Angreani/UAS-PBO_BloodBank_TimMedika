package org.bloodblank.model;

public class Admin extends User {
    private String levelAkses; // Contoh: "SuperAdmin" atau "Staff"

    public Admin(String username, String password, String nama, String levelAkses) {
        super(username, password, nama);
        this.levelAkses = levelAkses;
    }

    public String getLevelAkses() { return levelAkses; }
    public void setLevelAkses(String levelAkses) { this.levelAkses = levelAkses; }
}