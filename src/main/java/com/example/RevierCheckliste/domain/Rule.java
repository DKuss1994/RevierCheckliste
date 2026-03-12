package com.example.RevierCheckliste.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Rule {
    LocalDate startDay;
    LocalDate endDay;




    public Rule(LocalDate startDay, LocalDate endDay) {
        this.startDay =Objects.requireNonNull( startDay,"Rule start day must not be null");
        this.endDay = Objects.requireNonNull(  endDay,"Rule end day must not be null");

        if (startDay.isAfter(endDay)) {
            throw new IllegalArgumentException("Rule start day must not be after end day");
        }
     }

    public LocalDate getEndDay() {
        return endDay;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public boolean isActive(LocalDate day)   {
        return day.isBefore(endDay)&& day.isAfter(startDay);
    }
}
