package com.example.securitydispatch.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<ChecklistEntity,Long> {
}
