package org.bloodblank.donordarahapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stok_darah")
public class StokDarah {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String golongan;

    private Integer jumlahStok;

    private String rumahSakit;

    public StokDarah() {
    }

    public Long getId() {
        return id;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public Integer getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(Integer jumlahStok) {
        this.jumlahStok = jumlahStok;
    }

    public String getRumahSakit() {
        return rumahSakit;
    }

    public void setRumahSakit(String rumahSakit) {
        this.rumahSakit = rumahSakit;
    }
}
