package com.example.RevierCheckliste.domain;

public class Driver {
    private long id;
    private String firstName;
    private String lastName;
    public Driver(long id, String name, String lastName) {
        this.lastName = lastName;
        if(name == null||name.isBlank()||lastName == null||lastName.isBlank()){
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.id = id;
        this.firstName = name;
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
}
