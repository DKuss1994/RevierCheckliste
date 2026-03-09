package com.example.RevierCheckliste.domain;

public class Address {
    private final String street;
    private final String city;
    private final String ZIPCode;
    public Address( String street, String city, String zipCode) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street must not be blank");
        } if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City must not be blank");
        } if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("ZIP Code must not be blank");
        }

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
