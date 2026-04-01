package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.*;

import java.util.List;

public class DriverService {
    private final ZoneRepository zoneRepository;
    private final DriverRepository driverRepository;

    public DriverService(ZoneRepository zoneRepository, DriverRepository driverRepository) {
        this.zoneRepository = zoneRepository;
        this.driverRepository = driverRepository;
    }

    public Driver create(long id, String firstName, String lastName) {
        DriverEntity driver = new DriverEntity(id, firstName, lastName);
        return DriverMapper.toDomain(driverRepository.save(driver));
    }

    public Driver findById(long id) {
    return driverRepository.findById(id).map(DriverMapper::toDomain)
            .orElseThrow(() -> new IllegalArgumentException("Driver not found: " + id));
    }

    public List<Driver> findAll() {
        return driverRepository.findAll().stream().map(DriverMapper::toDomain).toList();
    }

    public Driver update(long id, String firstName, String lastName) {
        existDriverById(id);
        DriverEntity update = new DriverEntity(id, firstName, lastName);
        return DriverMapper.toDomain(driverRepository.save(update));
    }

    public void delete(long id) {
        existDriverById(id);
        driverRepository.deleteById(id);

    }

    public Driver assignZone(long driverId, long zoneId) {
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found: " + driverId));
        ZoneEntity zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new IllegalArgumentException("Zone not found: " + zoneId));
        driver.addAssignedZone(zone);
        return DriverMapper.toDomain(driverRepository.save(driver));
    }
    private void existDriverById(long id) {
        driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found: " + id));
    }
}
