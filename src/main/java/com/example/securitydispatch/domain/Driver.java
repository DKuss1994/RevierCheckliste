package com.example.securitydispatch.domain;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private long id;
    private String firstName;
    private String lastName;
    private List<Zone> assignedZones = new ArrayList<>();

    public Driver(long id, String name, String lastName) {
        if (name == null || name.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.lastName = lastName;

        this.id = id;
        this.firstName = name;
    }

    public void assignedZones(Zone zone) {
        assignedZones.add(zone);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Zone> getAssignedZones() {
        return assignedZones;
    }

    public void setAssignedZones(List<Zone> assignedZones) {
        this.assignedZones = assignedZones;
    }

    public boolean isQualifiedForZone(Zone zone) {
        return assignedZones.contains(zone);
    }
}