package com.example.securitydispatch.domain;

import java.util.Objects;

public class Warning {
    private final String message;

    public Warning(String message) {
        this.message = Objects.requireNonNull(message,"Warning message must not blank");
        if(message.isBlank()){
            throw new IllegalArgumentException("Warning message must not blank");
        }

    }

    public String getMessage() {
        return message;
    }
}
