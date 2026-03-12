package com.example.RevierCheckliste.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Shift {
    private final long id;
    private final Driver driver;
    private final Zone zone;
    private final LocalDate deploymentDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    public Shift(long id, Driver driver, Zone zone, LocalDate day, LocalTime start, LocalTime end) {
        this.id = id;
        this.driver = Objects.requireNonNull(driver,"Shift driver must not be null");
        this.zone = Objects.requireNonNull(zone,"Shift zone must not be null");
        this.deploymentDate = Objects.requireNonNull(day,"Shift deployment day must not be null");
        this.startTime = Objects.requireNonNull(start,"Shift start time must not be null");
        this.endTime = Objects.requireNonNull(end,"Shift end time must not be null");

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

    public boolean IsNightShift() {
        return false;
    }
}
