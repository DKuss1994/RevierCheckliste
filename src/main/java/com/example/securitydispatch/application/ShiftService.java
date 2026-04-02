package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.infrastructure.persistence.DriverEntity;
import com.example.securitydispatch.infrastructure.persistence.ZoneEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ShiftService {
    public Shift create(long id, long driverId, long zoneId, LocalDate deploymentDate, LocalTime startTime, LocalTime endTime) {
        return null;
    }

    public Shift findById(long id) {
        return null;
    }

    public List<Shift> findAll() {
        return null;
    }

    public void delete(long id) {

    }
}
