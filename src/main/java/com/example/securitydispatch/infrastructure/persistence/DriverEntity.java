package com.example.securitydispatch.infrastructure.persistence;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drivers")
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @ManyToMany
    @JoinTable(
            name = "drivers_zones",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "zone_id")
    )
    private List<ZoneEntity> assignedZones = new ArrayList<>();

    protected DriverEntity() {
    }

    public DriverEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<ZoneEntity> getAssignedZones() {
        return assignedZones;
    }
    public void addAssignedZone(ZoneEntity zone){
        assignedZones.add(zone);
    }
}
