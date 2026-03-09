package com.example.RevierCheckliste.domain;

public class SecurityObject {


    private final long id;
    private final String name;
    private final Zone zone;
    private final Address address;

    public SecurityObject(long id, String name, Zone zone, Address address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Object name must not be blank");
        } if (zone == null ) {
            throw new IllegalArgumentException("Zone name must not be null");
        } if (address == null ) {
            throw new IllegalArgumentException("Address name must not be null");
        }
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Zone getZone() {
        return zone;
    }

    public Address getAddress() {
        return address;
    }


}
