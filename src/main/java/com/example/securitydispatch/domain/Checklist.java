package com.example.securitydispatch.domain;

import java.time.LocalDateTime;

public class Checklist {
    private final long id;
    private final Shift shift;
    private final StandardConfiguration config;
    LocalDateTime today;

    public Checklist(long id,Shift shift,StandardConfiguration config,LocalDateTime today) {
        if(shift == null){
           throw new IllegalArgumentException("Checklist shift must not be null");
        }
        this.today = today;
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

    public LocalDateTime getToday() {
        return today;
    }
}
