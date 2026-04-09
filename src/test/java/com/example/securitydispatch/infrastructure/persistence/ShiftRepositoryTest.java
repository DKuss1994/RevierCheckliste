package com.example.securitydispatch.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ShiftRepositoryTest {
    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private SecurityObjectRepository securityObjectRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Test
    void shouldSaveAndLoadShift() {
        LocalDate startDay = LocalDate.of(2026, 3, 3);
        LocalTime startTime = LocalTime.of(22, 0);
        LocalTime endTime = LocalTime.of(6, 0);
        ZoneEntity zone = new ZoneEntity( "Zone 1");
        zoneRepository.save(zone);
        DriverEntity driver = new DriverEntity( "Max", "Mustermann");
        driver.addAssignedZone(zone);
        driverRepository.save(driver);
        AddressEmbeddable address = new AddressEmbeddable
                ("Musterstraße 1", "Berlin", "10115");
        StandardConfigurationEmbeddable config = new StandardConfigurationEmbeddable(
                2,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY),
                null,
                null,
                null,
                null,
                null,
                null,
                "Test notes"
        );
        SecurityObjectEntity object = new SecurityObjectEntity(
                 "Object 1", zone, address, config);
        securityObjectRepository.save(object);


        ShiftEntity shift = new ShiftEntity(
                1L, driver, zone, startDay, startTime, endTime);
        shiftRepository.save(shift);
        Optional<ShiftEntity> found = shiftRepository.findById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getDeploymentDate()).isEqualTo(startDay);
    }
}
