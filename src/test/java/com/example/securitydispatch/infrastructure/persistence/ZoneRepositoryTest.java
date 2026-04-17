package com.example.securitydispatch.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
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
        ZoneEntity zone = zoneRepository.save(new ZoneEntity("Zone 1"));
        zoneRepository.deleteById(zone.getId());
        Optional<ZoneEntity> found = zoneRepository.findById(zone.getId());

        assertThat(found).isEmpty();
    }
    @Test
    void shouldFindZoneByNameContaining() {
        zoneRepository.save(new ZoneEntity("Zone 1"));
        zoneRepository.save(new ZoneEntity("Zone 2"));
        zoneRepository.save(new ZoneEntity("Sonderzone"));

        List<ZoneEntity> result = zoneRepository
                .findByNameContainingIgnoreCase("zone");

        assertThat(result).hasSize(3);
    }

    @Test
    void shouldFindZoneByNameCaseInsensitive() {
        zoneRepository.save(new ZoneEntity("Zone 1"));

        List<ZoneEntity> result = zoneRepository
                .findByNameContainingIgnoreCase("ZONE");

        assertThat(result).hasSize(1);
    }
}
