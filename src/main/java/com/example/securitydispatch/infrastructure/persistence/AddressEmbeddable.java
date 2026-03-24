package com.example.securitydispatch.infrastructure.persistence;
import jakarta.persistence.Embeddable;
@Embeddable
public class AddressEmbeddable {
    private String street;
    private String city;
    private String postalCode;

    protected AddressEmbeddable(){}

    public AddressEmbeddable(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
