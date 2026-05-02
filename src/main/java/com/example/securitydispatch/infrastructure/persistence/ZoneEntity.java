package com.example.securitydispatch.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "zones")
public class ZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    protected ZoneEntity() {
    } // JPA braucht leeren Konstruktor

    public ZoneEntity(String name) {

        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
