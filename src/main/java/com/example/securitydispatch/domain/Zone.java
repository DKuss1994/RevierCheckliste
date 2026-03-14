package com.example.securitydispatch.domain;

import java.util.Objects;

public class Zone {
    private long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone = (Zone) o;
        return id == zone.id && Objects.equals(name, zone.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Zone(long id, String name) {
        if(name == null||name.isBlank()){
            throw new IllegalArgumentException("Zone must not be blank");
        }
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
