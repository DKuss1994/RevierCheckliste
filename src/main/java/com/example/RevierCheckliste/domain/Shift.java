package com.example.RevierCheckliste.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Shift {
    private final long id;
    private final Driver driver;
    private final Zone zone;
    private final LocalDate deploymentDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    public Shift(long id, Driver driver, Zone zone, LocalDate day, LocalTime start, LocalTime end) {
        this.id = id;
        this.driver = driver;
        this.zone = zone;
        this.deploymentDate = day;
        this.startTime = start;
        this.endTime = end;

    }

    public long getId() {
        return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public Zone getZone() {
        return zone;
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
