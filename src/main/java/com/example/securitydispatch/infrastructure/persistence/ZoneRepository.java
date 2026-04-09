package com.example.securitydispatch.infrastructure.persistence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<ZoneEntity, Long> {
    List<ZoneEntity> findByNameContainingIgnoreCase(String name);
}
