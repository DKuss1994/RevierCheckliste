package com.example.securitydispatch.domain;

public class Warning {
    private final String message;

    public Warning(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
