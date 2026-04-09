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

    public SecurityObject create(String name, long zoneId, AddressEmbeddable address, StandardConfigurationEmbeddable standardConfiguration) {
        ZoneEntity zoneEntity = zoneRepository.findById(zoneId)
                .orElseThrow(()->new IllegalArgumentException("Zone not found: " + zoneId));

        SecurityObjectEntity objectEntity = new SecurityObjectEntity(name,zoneEntity,address,standardConfiguration);
        return SecurityObjectMapper.toDomain(securityObjectRepository.save(objectEntity));
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
        SecurityObjectEntity securityObject = new SecurityObjectEntity(
                name,zoneEntity,address,standardConfiguration
        );
        return SecurityObjectMapper.toDomain(securityObjectRepository.save(securityObject));
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
