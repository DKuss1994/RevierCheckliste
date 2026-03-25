package com.example.securitydispatch.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityObjectRepository extends JpaRepository<SecurityObjectEntity,Long> {
}
