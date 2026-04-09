package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.domain.Zone;

import java.util.ArrayList;
import java.util.List;

public class DriverRequest {

    private String firstName;
    private String lastName;
    private final List<Zone> assignedZones = new ArrayList<>();

    public DriverRequest(){}


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Zone> getAssignedZones() {
        return assignedZones;
    }
}
