package org.bloodblank.donordarahapi.controller;

import org.bloodblank.donordarahapi.entity.StokDarah;
import org.bloodblank.donordarahapi.service.StokDarahService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stok")
public class StokDarahController {

    private final StokDarahService service;

    public StokDarahController(StokDarahService service) {
        this.service = service;
    }

    @GetMapping
    public List<StokDarah> getAll() {
        return service.getAll();
    }

    @GetMapping("/dummy")
    public StokDarah dummy() {

        StokDarah stok = new StokDarah();

        stok.setGolongan("A");
        stok.setJumlahStok(25);
        stok.setRumahSakit("RS Adam Malik");

        return service.save(stok);
    }
}
