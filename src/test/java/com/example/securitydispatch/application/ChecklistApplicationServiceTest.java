package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.infrastructure.persistence.*;
import com.example.securitydispatch.infrastructure.web.ChecklistRequest;
import com.example.securitydispatch.infrastructure.web.ChecklistResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChecklistApplicationServiceTest {
    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private SecurityObjectRepository securityObjectRepository;

    @Mock
    private ChecklistRepository checklistRepository;

    @Mock
    private ChecklistGenerationService checklistGenerationService;

    @InjectMocks
    private ChecklistApplicationService checklistApplicationService;

    private final ZoneEntity zoneEntity = new ZoneEntity("Zone 1");
    private final DriverEntity driverEntity = new DriverEntity("Max", "Mustermann");
    private final ShiftEntity shiftEntity = new ShiftEntity(
            1L,
            driverEntity,
            zoneEntity,
            LocalDate.of(2026, 3, 30),
            LocalTime.of(18, 0),
            LocalTime.of(6, 0));
    private final StandardConfigurationEmbeddable configurationEmbeddable = new StandardConfigurationEmbeddable(2,
            null, null, null, null, null, null, null, null);
    private final AddressEmbeddable addressEmbeddable = new AddressEmbeddable("Musterstraße 1", "Musterstadt", "00000");
    private final SecurityObjectEntity securityObjectEntity = new SecurityObjectEntity(
            1L, "Object 1", zoneEntity, addressEmbeddable, configurationEmbeddable
    );

    @Test
    void shouldGenerateChecklistSuccessfully() {
        Zone zone = new Zone(1L, "Zone1");
        Driver driver = new Driver(1L, "Max", "Mustermann");
        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2026, 3, 30),
                LocalTime.of(18, 0),
                LocalTime.of(6, 0));
        StandardConfiguration configuration = new StandardConfiguration.Builder().inspectionCount(2).build();
        Checklist checklist = new Checklist(1L, shift, configuration, LocalDateTime.now(), List.of(), List.of());
        when(shiftRepository.findById(1L)).thenReturn(Optional.of(shiftEntity));
        when(securityObjectRepository.findByZoneId(anyLong())).thenReturn(List.of(securityObjectEntity));
        when(checklistGenerationService.generate(any(), any())).thenReturn(checklist);
        ChecklistEntity savedEntity = new ChecklistEntity(shiftEntity, configurationEmbeddable,
                LocalDateTime.now(), List.of());
        when(checklistRepository.save(any())).thenReturn(savedEntity);
        ChecklistResponse response = checklistApplicationService.generate
                (new ChecklistRequest(1L, 1L));


        assertThat(response.getInspectionCount()).isEqualTo(2);

    }

    @Test
    void shouldThrowExceptionWhenShiftNotFound() {
        when(shiftRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> checklistApplicationService.generate(
                new ChecklistRequest(99L, 1L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shift not found: 99");
    }

    @Test
    void shouldGenerateEmptyChecklistWhenNoObjectsInZone() {
        Zone zone = new Zone(1L, "Zone1");
        Driver driver = new Driver(1L, "Max", "Mustermann");
        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2026, 3, 30),
                LocalTime.of(18, 0), LocalTime.of(6, 0));
        StandardConfiguration configuration = new StandardConfiguration.Builder().build();
        Checklist checklist = new Checklist(1L, shift, configuration,
                LocalDateTime.now(), List.of(), List.of());

        when(shiftRepository.findById(1L)).thenReturn(Optional.of(shiftEntity));
        when(securityObjectRepository.findByZoneId(anyLong())).thenReturn(List.of());
        ChecklistEntity savedEntity = new ChecklistEntity( shiftEntity, configurationEmbeddable,
                LocalDateTime.now(), List.of());
        when(checklistRepository.save(any())).thenReturn(savedEntity);
        when(checklistGenerationService.generate(any(), any())).thenReturn(checklist);

        ChecklistResponse response = checklistApplicationService.generate(
                new ChecklistRequest(1L, 1L));

        assertThat(response).isNotNull();
    }

    @Test
    void shouldReturnChecklistDomain() {
        Zone zone = new Zone(1L, "Zone 1");
        Driver driver = new Driver(1L, "Max", "Mustermann");
        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2024, 3, 11),
                LocalTime.of(6, 0), LocalTime.of(14, 0));
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2).build();
        Checklist checklist = new Checklist(1L, shift, config, LocalDateTime.now(), List.of(), List.of());
        when(shiftRepository.findById(1L)).thenReturn(Optional.of(shiftEntity));
        when(securityObjectRepository.findByZoneId(anyLong())).thenReturn(List.of(securityObjectEntity));
        when(checklistGenerationService.generate(any(), any())).thenReturn(checklist);

        ChecklistEntity savedEntity = new ChecklistEntity( shiftEntity,
                configurationEmbeddable, LocalDateTime.now(), List.of());
        when(checklistRepository.save(any())).thenReturn(savedEntity);
        Checklist result = checklistApplicationService.generateChecklist(
                new ChecklistRequest(1L, 1L));


        assertThat(result.getConfiguration().getInspectionCount()).hasValue(2);

    }


}
