package com.example.securitydispatch.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DriverRepositoryTest {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    void shouldSaveAndFindDriver() {
        DriverEntity driver = new DriverEntity(1L, "Max", "Mustermann");
        driverRepository.save(driver);
        Optional<DriverEntity> found = driverRepository.findById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Max");
        assertThat(found.get().getLastName()).isEqualTo("Mustermann");
        assertThat(found.get().getId()).isEqualTo(1L);
        assertThat(found.get().getAssignedZones()).isEmpty();
    }

    @Test
    void shouldSaveDriverWithAssignedZone() {
        ZoneEntity zone = zoneRepository.save(new ZoneEntity("Zone 1"));
        DriverEntity driver = new DriverEntity(1L, "Max", "Mustermann");
        driver.addAssignedZone(zone);
        driverRepository.save(driver);
        Optional<DriverEntity> found = driverRepository.findById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getAssignedZones()).hasSize(1);
        assertThat(found.get().getAssignedZones().getFirst().getName()).isEqualTo("Zone 1");

    }

}
