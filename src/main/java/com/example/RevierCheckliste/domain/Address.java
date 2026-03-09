package com.example.RevierCheckliste.domain;

public class Address {
    private final String street;
    private final String city;
    private final String ZIPCode;
    public Address( String street, String city, String zipCode) {

        this.street = street;
        this.city = city;
        this.ZIPCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZIPCode() {
        return ZIPCode;
    }
}
