package com.example.securitydispatch.infrastructure.web;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShiftRequest {
    private long id;
    private long zoneId;
    private long driverId;
    private LocalDate deploymentDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public ShiftRequest(){}

    public long getId() {
        return id;
    }

    public long getZoneId() {
        return zoneId;
    }

    public long getDriverId() {
        return driverId;
    }

    public LocalDate getDeploymentDate() {
        return deploymentDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
