package com.example.securitydispatch.infrastructure.web;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class SecurityObjectRequest {
    private String name;
    private long zoneId;

    // Address
    private String street;
    private String city;
    private String postalCode;

    // StandardConfiguration
    private Integer inspectionCount;
    private Set<DayOfWeek> inspectionDays;
    private LocalTime openingTime;
    private Set<DayOfWeek> openingDays;
    private LocalTime closingTime;
    private Set<DayOfWeek> closingDays;
    private LocalTime inspectionWindowStart;
    private LocalTime inspectionWindowEnd;
    private String notes;
    public SecurityObjectRequest(){}


    public String getName() {
        return name;
    }

    public long getZoneId() {
        return zoneId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Integer getInspectionCount() {
        return inspectionCount;
    }

    public Set<DayOfWeek> getInspectionDays() {
        return inspectionDays;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public Set<DayOfWeek> getOpeningDays() {
        return openingDays;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public Set<DayOfWeek> getClosingDays() {
        return closingDays;
    }

    public LocalTime getInspectionWindowStart() {
        return inspectionWindowStart;
    }

    public LocalTime getInspectionWindowEnd() {
        return inspectionWindowEnd;
    }

    public String getNotes() {
        return notes;
    }
}
