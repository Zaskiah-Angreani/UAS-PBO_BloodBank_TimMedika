package org.bloodblank.donordarahapi.controller;

import jakarta.validation.Valid;
import org.bloodblank.donordarahapi.entity.Donor;
import org.bloodblank.donordarahapi.service.DonorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping
    public Donor createDonor(@Valid @RequestBody Donor donor) {
        return donorService.save(donor);
    }

    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/dummy")
    public Donor createDummyDonor() {

        Donor donor = new Donor();

        donor.setNama("Andi");
        donor.setGolDarah("O");
        donor.setBeratBadan(65);
        donor.setStatus("TERSEDIA");

        return donorService.save(donor);
    }
}

