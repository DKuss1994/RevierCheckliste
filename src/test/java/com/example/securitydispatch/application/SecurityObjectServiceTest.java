package com.example.securitydispatch.application;


import com.example.securitydispatch.domain.SecurityObject;

import com.example.securitydispatch.infrastructure.persistence.*;
import org.junit.jupiter.api.Test;

import com.example.securitydispatch.infrastructure.persistence.ZoneEntity;

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
public class SecurityObjectServiceTest {
    @Mock
    private ZoneRepository zoneRepository;
    @Mock
    private SecurityObjectRepository securityObjectRepository;
    @InjectMocks
    private SecurityObjectService securityObjectService;

    private ZoneEntity zoneEntity = new ZoneEntity(1L, "Zone 1");
    private AddressEmbeddable address = new AddressEmbeddable("Musterstraße 1", "Musterstadt", "123456");
    private StandardConfigurationEmbeddable standardConfiguration = new StandardConfigurationEmbeddable(2,
            null, null, null, null, null, null, null, null);

    @Test
    void shouldCreateSecurityObject() {
        SecurityObjectEntity saved =
                new SecurityObjectEntity(1L, "Object A", zoneEntity, address, standardConfiguration);
        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zoneEntity));
        when(securityObjectRepository.save(any())).thenReturn(saved);

        SecurityObject result = securityObjectService.create(
                1L,
                "Object A",
                1L,
                address,
                standardConfiguration);
        assertThat(result.getName()).isEqualTo("Object A");
    }

    @Test
    void shouldFindSecurityObjectById() {
        when(securityObjectRepository.findById(1L)).
                thenReturn(Optional.of(
                        new SecurityObjectEntity
                                (1L, "Object A", zoneEntity, address, standardConfiguration)));
        SecurityObject result = securityObjectService.findById(1L);
        assertThat(result.getName()).isEqualTo("Object A");
    }

    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        when(securityObjectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> securityObjectService.findById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SecurityObject not found: 99");
    }

    @Test
    void shouldFindAllSecurityObjects() {
        when(securityObjectRepository.findAll()).thenReturn(List.of(
                new SecurityObjectEntity(1L, "Object A", zoneEntity, address, standardConfiguration),
                new SecurityObjectEntity(2L, "Object B", zoneEntity, address, standardConfiguration)
        ));
        List<SecurityObject> result = securityObjectService.findAll();
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getName()).isEqualTo("Object A");
        assertThat(result.get(1).getName()).isEqualTo("Object B");
    }

    @Test
    void shouldUpdateSecurityObject() {
        ZoneEntity oldZone = new ZoneEntity(1L, "Zone 1");
        ZoneEntity newZone = new ZoneEntity(2L, "Zone 2");
        SecurityObjectEntity existing = new SecurityObjectEntity(1L, "Object A", oldZone, address, standardConfiguration);
        SecurityObjectEntity update = new SecurityObjectEntity(
                1L, "New Name", newZone, address, standardConfiguration
        );
        when(securityObjectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(zoneRepository.findById(2L)).thenReturn(Optional.of(newZone));
        when(securityObjectRepository.save(any())).thenReturn(update);
        SecurityObject result = securityObjectService.update(1L, "New Name", 2L, address, standardConfiguration);
        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getZone().getName()).isEqualTo("Zone 2");
    }



    @Test
    void shouldDeleteSecurityObject(){
        when(securityObjectRepository.findById(1L)).thenReturn(Optional.of(new SecurityObjectEntity
                (1L,"Object A",zoneEntity,address,standardConfiguration)));
        securityObjectService.delete(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingSecurityObject(){
        when(securityObjectRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(()->securityObjectService.delete(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SecurityObject not found: 99");
    }
    @Test
    void shouldThrowExceptionWhenUpdatingWithNonExistingZone() {
        when(securityObjectRepository.findById(1L))
                .thenReturn(Optional.of(new SecurityObjectEntity(
                        1L, "Object A", zoneEntity, address, standardConfiguration)));
        when(zoneRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> securityObjectService.update(
                1L, "New Name", 99L, address, standardConfiguration))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone not found: 99");
    }

}
