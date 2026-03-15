package com.example.securitydispatch.domain;

import java.time.LocalDateTime;

public class Checklist {
    private final long id;
    private final Shift shift;
    private final StandardConfiguration config;
    LocalDateTime generatedAt;

    public Checklist(long id,Shift shift,StandardConfiguration config,LocalDateTime generatedAt) {
        if(shift == null){
           throw new IllegalArgumentException("Checklist shift must not be null");
        }if(config == null){
           throw new IllegalArgumentException("Checklist configuration must not be null");
        }if(generatedAt == null){
           throw new IllegalArgumentException("Checklist generatedAt must not be null");
        }
        this.generatedAt = generatedAt;
        this.config = config;
        this.shift = shift;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Shift getShift() {
        return shift;
    }

    public StandardConfiguration getConfig() {
        return config;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}
