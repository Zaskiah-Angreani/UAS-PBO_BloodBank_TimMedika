package org.bloodblank.donordarahapi.service;

import org.bloodblank.donordarahapi.entity.StokDarah;
import org.bloodblank.donordarahapi.repository.StokDarahRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StokDarahService {

    private final StokDarahRepository repository;

    public StokDarahService(StokDarahRepository repository) {
        this.repository = repository;
    }

    public StokDarah save(StokDarah stok) {
        return repository.save(stok);
    }

    public List<StokDarah> getAll() {
        return repository.findAll();
    }
}
