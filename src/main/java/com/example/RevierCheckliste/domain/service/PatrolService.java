package com.example.RevierCheckliste.domain.service;

import java.time.LocalDate;

public class PatrolService {
    private LocalDate serviceDate;
    public PatrolService(){

    }
    public PatrolService(LocalDate serviceDate){
        this.serviceDate = serviceDate;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }


}
