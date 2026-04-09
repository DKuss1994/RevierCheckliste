package com.example.securitydispatch.infrastructure.persistence;
import com.example.securitydispatch.domain.Warning;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class ChecklistRepositoryTest {
    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    ChecklistRepository checklistRepository;
    @Autowired
    SecurityObjectRepository securityObjectRepository;
    @Autowired
    ZoneRepository zoneRepository;
    @Autowired
    DriverRepository driverRepository;
    @Test
    void shouldSaveAndLoadChecklist(){
        LocalDate startDay = LocalDate.of(2026, 3, 3);
        LocalTime startTime = LocalTime.of(22, 0);
        LocalTime endTime = LocalTime.of(6, 0);
        ZoneEntity zone = new ZoneEntity(1L, "Zone 1");
        zoneRepository.save(zone);
        DriverEntity driver = new DriverEntity(1L, "Max", "Mustermann");
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
                1L, "Object 1", zone, address, config);
        securityObjectRepository.save(object);


        ShiftEntity shift = new ShiftEntity(
                1L, driver, zone, startDay, startTime, endTime);
        shiftRepository.save(shift);
        Warning warning = new Warning("Stop");
        List<Warning> warnings = List.of(warning);
        LocalDateTime generatedAt = LocalDateTime.now();
        ChecklistEntity checklist = new ChecklistEntity(shift,config,generatedAt,warnings);
        checklistRepository.save(checklist);
        Optional<ChecklistEntity> found = checklistRepository.findById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getGeneratedAt()).isEqualTo(generatedAt);
        assertThat(found.get().getConfiguration().getInspectionDays()).isEqualTo(config.getInspectionDays());
        assertThat(found.get().getShift().getDriver().getFirstName())
                .isEqualTo(driver.getFirstName());
        assertThat(found.get().getWarnings().getFirst().getMessage()).isEqualTo("Stop");
        assertThat(found.get().getWarnings()).hasSize(1);


    }
    @Test
    void shouldFindSecurityObjectsByZoneId(){
        ZoneEntity zone = new ZoneEntity(1L,"Zone 1");
        zoneRepository.save(zone);
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
        securityObjectRepository.save(
                new SecurityObjectEntity(1L, "Object A", zone, address, config));
        securityObjectRepository.save(
                new SecurityObjectEntity(2L, "Object B", zone, address, config));
        List<SecurityObjectEntity> result = securityObjectRepository.findByZoneId(1L);

        assertThat(result).hasSize(2);
    }

}
