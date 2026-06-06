package org.bloodblank.model;

public class Admin extends User {
    private String levelAkses;

    public Admin(String username, String password, String nama, String levelAkses) {
        super(username, password, nama);
        this.levelAkses = levelAkses;
    }

    public String getLevelAkses() { return levelAkses; }
    public void setLevelAkses(String levelAkses) { this.levelAkses = levelAkses; }

    @Override
    public String getRole() { return "ADMIN"; }

    @Override
    public String getDisplayInfo() { return "Admin: " + getNama() + " (" + levelAkses + ")"; }
}