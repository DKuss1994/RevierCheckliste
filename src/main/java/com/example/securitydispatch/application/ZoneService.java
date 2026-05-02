package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.ZoneEntity;
import com.example.securitydispatch.infrastructure.persistence.ZoneMapper;
import com.example.securitydispatch.infrastructure.persistence.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.List;
@Service
public class ZoneService {
    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }


    public Zone create(String name) {
        ZoneEntity entity = new ZoneEntity( name);
        return ZoneMapper.toDomain(zoneRepository.save(entity));
    }

    public Zone findById(long id) {
        return zoneRepository.findById(id).map(
                ZoneMapper::toDomain
        ).orElseThrow(() -> new IllegalArgumentException("Zone not found: " + id));
    }

    public List<Zone> findAll() {
        return zoneRepository.findAll()
                .stream().map(ZoneMapper::toDomain)
                .toList();
    }

    public Zone update(long id, String name) {
        ZoneEntity existing = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
        existing.setName(name);
        return ZoneMapper.toDomain(zoneRepository.save(existing));
    }

    private void existZoneById(long id) {
        zoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Zone not found: " + id));
    }

    public void delete(long id) {
        existZoneById(id);
        zoneRepository.deleteById(id);
    }

    public List<Zone> search(String name) {
        return zoneRepository.findByNameContainingIgnoreCase(name)
                .stream().map(ZoneMapper::toDomain)
                .toList();
    }
}
