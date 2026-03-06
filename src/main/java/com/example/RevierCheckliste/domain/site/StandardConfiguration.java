package com.example.RevierCheckliste.domain.site;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class StandardConfiguration {
    private int controlCount;
    private Set<DayOfWeek> allowedWeekdays = new HashSet<DayOfWeek>();
    public StandardConfiguration(int controlCount){
        this.controlCount = controlCount;
    }

    public int getControlCount() {
        return controlCount;
    }

    public Set<DayOfWeek> getAllowedWeekdays() {
        return allowedWeekdays;
    }

    public void setAllowedWeekdays(Set<DayOfWeek> allowedWeekdays) {
        this.allowedWeekdays = allowedWeekdays;
    }
}
