package com.example.RevierCheckliste.domain.service;

import java.time.LocalDate;

public class PatrolService {
    private LocalDate localDate;
    public PatrolService(){

    }
    public PatrolService(LocalDate localDate){
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
