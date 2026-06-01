package org.bloodblank.model;

public class Donor extends User {
    private String golonganDarah;

    public Donor(String username, String password, String nama, String golonganDarah) {
        super(username, password, nama); // Memanggil constructor dari class User
        this.golonganDarah = golonganDarah;
    }

    public String getGolonganDarah() { return golonganDarah; }
    public void setGolonganDarah(String golonganDarah) { this.golonganDarah = golonganDarah; }
}