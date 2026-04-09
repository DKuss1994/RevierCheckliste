package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTest {
    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private ShiftRepository shiftRepository;

    @InjectMocks
    private ShiftService shiftService;

    private final DriverEntity driverEntity = new DriverEntity(1L,"Max","Mustermann");
    private final ZoneEntity zoneEntity = new ZoneEntity("Zone 1");
    private final LocalDate deploymentDate = LocalDate.of(2024, 3, 11);
    private final LocalTime startTime = LocalTime.of(6, 0);
    private final LocalTime endTime = LocalTime.of(14, 0);


    @Test
    void shouldCreateShift() {
        ShiftEntity saved = getShiftEntity();
        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zoneEntity));
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driverEntity));
        when(shiftRepository.save(any())).thenReturn(saved);
        Shift result = shiftService.
                create(1L, 1L,1L,deploymentDate,startTime,endTime);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getZone().getName()).isEqualTo("Zone 1");
        assertThat(result.getDriver().getFirstName()).isEqualTo("Max");
        assertThat(result.getDeploymentDate()).isEqualTo(LocalDate.of(2024, 3, 11));
    }



    @Test
    void shouldShiftFindById() {
        when(shiftRepository.findById(1L)).
                thenReturn(Optional.of(getShiftEntity()));
        Shift result = shiftService.findById(1L);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getZone().getName()).isEqualTo("Zone 1");
        assertThat(result.getDriver().getFirstName()).isEqualTo("Max");
        assertThat(result.getDeploymentDate()).isEqualTo(LocalDate.of(2024, 3, 11));
    }
    @Test
    void shouldThrowExceptionWhenIdNotFound(){
        when(shiftRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> shiftService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shift not found: 99");
    }
    @Test
    void shouldFindAllShifts(){
        when(shiftRepository.findAll()).thenReturn(List.of(
               getShiftEntity(),
                getShiftEntity2())
        );
        List<Shift> result = shiftService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }
    @Test
    void shouldDeleteShift(){
        when(shiftRepository.findById(1L)).thenReturn(Optional.of(getShiftEntity()));
        shiftService.delete(1L);
    }
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingShift(){
        when(shiftRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> shiftService.delete(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shift not found: 99");
    }
    private @NonNull ShiftEntity getShiftEntity() {
        return new ShiftEntity(1L, driverEntity,zoneEntity,deploymentDate,startTime,endTime);
    } private @NonNull ShiftEntity getShiftEntity2() {
        return new ShiftEntity(2L, driverEntity,zoneEntity,deploymentDate,startTime,endTime);
    }
}
