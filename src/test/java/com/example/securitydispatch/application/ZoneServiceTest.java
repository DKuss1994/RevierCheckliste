package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Zone;
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
 class ZoneServiceTest {
    @Mock
    private ZoneRepository zoneRepository;

    @InjectMocks
    private ZoneService zoneService;

    @Test
    void shouldCreateZone() {
        ZoneEntity saved = new ZoneEntity("Zone 1");
        when(zoneRepository.save(any())).thenReturn(saved);
        Zone result = zoneService.create("Zone 1");

        assertThat(result.getName()).isEqualTo("Zone 1");
    }

    @Test
    void shouldZoneFindById() {
        when(zoneRepository.findById(1L)).
                thenReturn(Optional.of(new ZoneEntity("Zone 1")));
        Zone result = zoneService.findById(1L);
        assertThat(result.getName()).isEqualTo("Zone 1");
    }
    @Test
    void shouldThrowExceptionWhenIdNotFound(){
        when(zoneRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()->zoneService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone not found: 99");
    }
    @Test
    void shouldFindAllZones(){
        when(zoneRepository.findAll()).thenReturn(List.of(
                new ZoneEntity("Zone 1"),
                new ZoneEntity("Zone 2")
        ));
        List<Zone> result = zoneService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getName()).isEqualTo("Zone 1");
        assertThat(result.get(1).getName()).isEqualTo("Zone 2");
    }
    @Test
    void shouldUpdateZone(){
        ZoneEntity update = new ZoneEntity("Zone 1 Updated");
        when(zoneRepository.findById(1L)).thenReturn(Optional.of(new ZoneEntity("Zone 1")));
        when(zoneRepository.save(any())).thenReturn(update);
        Zone result = zoneService.update(1L,"Zone 1 Updated");
        assertThat(result.getName()).isEqualTo("Zone 1 Updated");
    }
    @Test
    void shouldDeleteZone(){
        when(zoneRepository.findById(1L)).thenReturn(Optional.of(new ZoneEntity("Zone 1")));
        zoneService.delete(1L);
    }
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingZone(){
when(zoneRepository.findById(99L)).thenReturn(Optional.empty());
assertThatThrownBy(()->zoneService.delete(99L))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Zone not found: 99");
    }
    @Test
    void shouldSearchZonesByName() {
        when(zoneRepository.findByNameContainingIgnoreCase("zone"))
                .thenReturn(List.of(
                        new ZoneEntity("Zone 1"),
                        new ZoneEntity("Zone 2")));

        List<Zone> result = zoneService.search("zone");

        assertThat(result).hasSize(2);
    }
}

