package com.example.securitydispatch.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "zones")
public class ZoneEntity {

    @Id
    private long id;
    private String name;

    protected ZoneEntity() {} // JPA braucht leeren Konstruktor

    public ZoneEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public String getName() { return name; }
}
