package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChecklistGenerationService {

    private final ConfigurationResolver resolver = new ConfigurationResolver();

    public Checklist generate(Shift shift, SecurityObject securityObject) {
        ResolutionResult result = resolver.resolve(
                shift.getDeploymentDate(), securityObject);
        List<Warning> warnings = new ArrayList<>(result.getWarnings());
        result.getConfiguration().getOpeningTime().ifPresent(openingTime -> {
            if (!isWithinShiftHours(openingTime, shift)) {
                warnings.add(new Warning("Opening time " + openingTime + " is outside shift hours"));
            }
        });result.getConfiguration().getClosingTime().ifPresent(closingTime -> {
            if (!isWithinShiftHours(closingTime, shift)) {
                warnings.add(new Warning("Closing time " + closingTime + " is outside shift hours"));
            }
        });

        return new Checklist(1L, shift, result.getConfiguration(), LocalDateTime.now(), warnings, List.of());
    }

    private boolean isWithinShiftHours(LocalTime time, Shift shift) {
        if (shift.isNightShift()) {
            return !time.isBefore(shift.getStartTime()) || !time.isAfter(shift.getEndTime());
        } else {
            return !time.isBefore(shift.getStartTime()) && !time.isAfter(shift.getEndTime());

        }
    }

}