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
        DriverEntity driver = driverRepository.save(new DriverEntity("Max", "Mustermann"));
        Optional<DriverEntity> found = driverRepository.findById(driver.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Max");
        assertThat(found.get().getLastName()).isEqualTo("Mustermann");
        assertThat(found.get().getId()).isEqualTo(driver.getId());
        assertThat(found.get().getAssignedZones()).isEmpty();
    }

    @Test
    void shouldSaveDriverWithAssignedZone() {
        ZoneEntity zone = zoneRepository.save(new ZoneEntity("Zone 1"));
        DriverEntity driver = driverRepository.save(new DriverEntity("Max", "Mustermann"));
        driver.addAssignedZone(zone);
        Optional<DriverEntity> found = driverRepository.findById(driver.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getAssignedZones()).hasSize(1);
        assertThat(found.get().getAssignedZones().getFirst().getName()).isEqualTo("Zone 1");

    }

}
