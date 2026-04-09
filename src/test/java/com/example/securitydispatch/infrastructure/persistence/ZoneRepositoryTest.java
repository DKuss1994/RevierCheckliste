package com.example.securitydispatch.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ZoneRepositoryTest {
    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    void shouldFindAndSaveZone() {
        ZoneEntity zone = zoneRepository.save(new ZoneEntity("Zone 1"));
        Optional<ZoneEntity> found = zoneRepository.findById(zone.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Zone 1");
        assertThat(found.get().getId()).isEqualTo(zone.getId());
    }

    @Test
    void shouldDeleteZone() {
        ZoneEntity zone = new ZoneEntity("Zone 1");
        zoneRepository.save(zone);
        zoneRepository.deleteById(1L);
        Optional<ZoneEntity> found = zoneRepository.findById(1L);

        assertThat(found).isEmpty();
    }
}
