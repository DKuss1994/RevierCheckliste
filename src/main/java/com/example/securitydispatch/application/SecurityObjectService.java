package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SecurityObjectService {
    private final ZoneRepository zoneRepository;
    private final SecurityObjectRepository securityObjectRepository;

    public SecurityObjectService(ZoneRepository zoneRepository, SecurityObjectRepository securityObjectRepository) {
        this.zoneRepository = zoneRepository;
        this.securityObjectRepository = securityObjectRepository;
    }

    public SecurityObject create(SecurityObject object) {
        ZoneEntity zoneEntity = zoneRepository.findById(object.getZone().getId())
                .orElseThrow(()->new IllegalArgumentException("Zone not found: " + object.getZone().getId()));
        // Baue AddressEmbeddable aus dem Domain-Objekt
        AddressEmbeddable address = new AddressEmbeddable(
                object.getAddress().getStreet(),
                object.getAddress().getCity(),
                object.getAddress().getZIPCode()
        );

        // Baue StandardConfigurationEmbeddable aus dem Domain-Objekt (nur die Felder, die du brauchst)
        StandardConfigurationEmbeddable config = new StandardConfigurationEmbeddable(
                object.getStandardConfiguration().getInspectionCount().orElse(null),
                object.getStandardConfiguration().getInspectionDays().orElse(null),
                object.getStandardConfiguration().getOpeningTime().orElse(null),
                object.getStandardConfiguration().getOpeningDays().orElse(null),
                object.getStandardConfiguration().getClosingTime().orElse(null),
                object.getStandardConfiguration().getClosingDays().orElse(null),
                object.getStandardConfiguration().getInspectionWindowStart().orElse(null),
                object.getStandardConfiguration().getInspectionWindowEnd().orElse(null),
                object.getStandardConfiguration().getNotes().orElse(null)
        );
        SecurityObjectEntity entity = new SecurityObjectEntity(object.getName(), zoneEntity, address, config);
        return SecurityObjectMapper.toDomain(securityObjectRepository.save(entity));
    }

    public SecurityObject findById(long id){
        return securityObjectRepository.findById(id).map(
                SecurityObjectMapper::toDomain
        ).orElseThrow(() -> new IllegalArgumentException("SecurityObject not found: " + id));
    }

    public List<SecurityObject> findAll() {
        return securityObjectRepository.findAll().stream()
                .map(SecurityObjectMapper::toDomain).toList();

    }


    public SecurityObject update(long id, String name, long zoneId, AddressEmbeddable address, StandardConfigurationEmbeddable standardConfiguration) {
        SecurityObjectEntity oldSecurity = securityObjectRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("SecurityObject not found: "+ id));
        ZoneEntity zoneEntity = zoneRepository.findById(zoneId)
                .orElseThrow(()->new IllegalArgumentException("Zone not found: " + zoneId));
        oldSecurity.setName(name);
        oldSecurity.setZone(zoneEntity);
        oldSecurity.setAddress(address);
        oldSecurity.setStandardConfiguration(standardConfiguration);
        return SecurityObjectMapper.toDomain(securityObjectRepository.save(oldSecurity));
    }

    public void delete(long id) {
        securityObjectRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("SecurityObject not found: "+ id));
        securityObjectRepository.deleteById(id);
    }

    public List<SecurityObject> search(String name) {
        return securityObjectRepository.findByNameContainingIgnoreCase(name)
                .stream().map(SecurityObjectMapper::toDomain)
                .toList();
    }
}
