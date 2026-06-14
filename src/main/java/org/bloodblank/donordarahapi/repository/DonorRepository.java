package org.bloodblank.donordarahapi.repository;

import org.bloodblank.donordarahapi.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
}
