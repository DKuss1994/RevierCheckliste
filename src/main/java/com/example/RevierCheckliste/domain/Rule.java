package com.example.RevierCheckliste.domain;

import java.time.LocalDate;

public class Rule {
    LocalDate startDay;
    LocalDate endDay;




    public Rule(LocalDate endDay, LocalDate startDay) {
        this.endDay = endDay;
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public boolean isActive(LocalDate day)   {
        return false;
    }
}
