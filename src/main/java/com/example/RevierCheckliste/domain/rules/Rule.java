package com.example.RevierCheckliste.domain.rules;

import java.time.LocalDate;

public abstract class Rule {

    protected LocalDate startDate;
    protected LocalDate endDate;

    public boolean isActive(LocalDate date){
        return !date.isBefore(startDate) &&
                !date.isAfter(endDate);
    }

    public abstract int apply(int standardValue);
}