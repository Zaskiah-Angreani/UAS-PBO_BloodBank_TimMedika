package org.bloodblank.donordarahapi.repository;

import org.bloodblank.donordarahapi.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
