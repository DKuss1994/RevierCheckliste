package com.example.RevierCheckliste.domain;

public class Zone {
    private long id;
    private String name;
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
