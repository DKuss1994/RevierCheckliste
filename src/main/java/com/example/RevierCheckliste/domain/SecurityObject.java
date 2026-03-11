package com.example.RevierCheckliste.domain;

public class SecurityObject {


    private final long id;
    private final String name;
    private final Zone zone;
    private final Address address;
    private final StandardConfiguration config;

    public SecurityObject(long id, String name, Zone zone, Address address, StandardConfiguration config) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Object name must not be blank");
        } if (zone == null ) {
            throw new IllegalArgumentException("Zone name must not be null");
        } if (address == null ) {
            throw new IllegalArgumentException("Address name must not be null");
        }if (config == null ) {
            throw new IllegalArgumentException("Configuration must not be null");
        }
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.address = address;
        this.config = config;
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


    public StandardConfiguration getStandardCofiguration() {
        return config;
    }
}
