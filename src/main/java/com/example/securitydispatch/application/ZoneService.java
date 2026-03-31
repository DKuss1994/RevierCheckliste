package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.ZoneEntity;
import com.example.securitydispatch.infrastructure.persistence.ZoneMapper;
import com.example.securitydispatch.infrastructure.persistence.ZoneRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;

public class ZoneService {
    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }


    public Zone create(long id, String name) {
        ZoneEntity entity = new ZoneEntity(id,name);
        return ZoneMapper.toDomain(zoneRepository.save(entity));
    }

    public Zone findById(long id) {
        return zoneRepository.findById(id).map(
                ZoneMapper::toDomain
        ).orElseThrow(()->new IllegalArgumentException("Zone not found: "+id));
    }

    public List<Zone>   findAll() {
    return zoneRepository.findAll()
            .stream().map(ZoneMapper::toDomain)
            .toList();
    }
}
