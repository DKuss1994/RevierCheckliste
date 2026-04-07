package com.example.securitydispatch.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityObjectRepository extends JpaRepository<SecurityObjectEntity,Long> {
    List<SecurityObjectEntity> findByZoneId(long zoneId);
}
