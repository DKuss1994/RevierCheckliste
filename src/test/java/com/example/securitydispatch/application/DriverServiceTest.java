package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.DriverEntity;
import com.example.securitydispatch.infrastructure.persistence.DriverRepository;
import com.example.securitydispatch.infrastructure.persistence.ZoneEntity;
import com.example.securitydispatch.infrastructure.persistence.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class DriverServiceTest {
    @Mock
    private DriverRepository driverRepository;

    @Mock
    private ZoneRepository zoneRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    void shouldCreateDriver() {
        DriverEntity saved = new DriverEntity(1L, "Max","Mustermann");
        when(driverRepository.save(any())).thenReturn(saved);
        Driver result = driverService.create(1L, "Max","Mustermann");

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Max");
        assertThat(result.getLastName()).isEqualTo("Mustermann");
    }

    @Test
    void shouldFindDriverById() {
        when(driverRepository.findById(1L)).
                thenReturn(Optional.of(new DriverEntity(1L, "Max","Mustermann")));
        Driver result = driverService.findById(1L);
        assertThat(result.getFirstName()).isEqualTo("Max");
        assertThat(result.getLastName()).isEqualTo("Mustermann");

    }
    @Test
    void shouldThrowExceptionWhenIdNotFound(){
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->driverService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Driver not found: 99");
    }
    @Test
    void shouldFindAllDrivers(){
        when(driverRepository.findAll()).thenReturn(List.of(
                new DriverEntity(1L,"Max","Mustermann"),
                new DriverEntity(2L,"Luis","Musterfrau")
        ));
        List<Driver> result = driverService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getFirstName()).isEqualTo("Max");
        assertThat(result.get(1).getFirstName()).isEqualTo("Luis");
    }
    @Test
    void shouldUpdateDriver(){
        DriverEntity update = new DriverEntity(1L,"Moritz","Schmidt");
        when(driverRepository.findById(1L)).thenReturn(Optional.of(new DriverEntity(1L,"Max","Mustermann")));
        when(driverRepository.save(any())).thenReturn(update);
        Driver result = driverService.update(1L,"Moritz","Schmidt");
        assertThat(result.getFirstName()).isEqualTo("Moritz");
    }
    @Test
    void shouldDeleteDriver(){
        when(driverRepository.findById(1L)).thenReturn(Optional.of(new DriverEntity(1L,"Max","Mustermann")));
        driverService.delete(1L);
    }
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingZone(){
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(()->driverService.delete(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Driver not found: 99");
    }
    @Test
    void shouldAssignZoneToDriver(){
        DriverEntity driver = new DriverEntity(1L,"Max","Mustermann");
        ZoneEntity zone = new ZoneEntity(1L,"Zone 1");

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zone));
        when(driverRepository.save(any())).thenReturn(driver);

        Driver result = driverService.assignZone(1L,1L);

        assertThat(result.getAssignedZones()).hasSize(1);
    }
    @Test
    void shouldThrowExceptionWhenAssigningNonExistingZone(){
        when(driverRepository.findById(1L)).thenReturn(Optional.of(new DriverEntity(1L,"Max","Mustermann")));
        when(zoneRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->driverService.assignZone(1L,99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone not found: 99");
    }

}
