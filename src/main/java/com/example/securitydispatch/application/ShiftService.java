package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
public class ShiftService {
    private final DriverRepository driverRepository;
    private final ZoneRepository zoneRepository;
    private final ShiftRepository shiftRepository;

    public ShiftService(DriverRepository driverRepository, ZoneRepository zoneRepository, ShiftRepository shiftRepository) {
        this.driverRepository = driverRepository;
        this.zoneRepository = zoneRepository;
        this.shiftRepository = shiftRepository;
    }

    public Shift create( long driverId, long zoneId, LocalDate deploymentDate, LocalTime startTime, LocalTime endTime) {
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found: " + driverId));
        ZoneEntity zoneEntity = zoneRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Zone not found: " + zoneId));
        ShiftEntity shiftEntity = new ShiftEntity( driverEntity, zoneEntity, deploymentDate, startTime, endTime);

        return ShiftMapper.toDomain(shiftRepository.save(shiftEntity));
    }

    public Shift findById(long id) {
        return shiftRepository.findById(id).map(
                ShiftMapper::toDomain
        ).orElseThrow(() -> new IllegalArgumentException("Shift not found: " + id));
    }

    public List<Shift> findAll() {
        return shiftRepository.findAll().stream()
                .map(ShiftMapper::toDomain).toList();
    }

    public void delete(long id) {
        shiftRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shift not found: " + id));
        shiftRepository.deleteById(id);
    }
}

