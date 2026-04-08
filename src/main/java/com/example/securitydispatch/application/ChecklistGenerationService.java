package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChecklistGenerationService {

    private final ConfigurationResolver resolver = new ConfigurationResolver();

    public Checklist generate(Shift shift, List<SecurityObject> securityObjects) {
        List<Warning> warnings = new ArrayList<>();
        List<ChecklistEntry> entries = new ArrayList<>();

        for (SecurityObject securityObject : securityObjects) {
            ResolutionResult result = resolver.resolve(
                    shift.getDeploymentDate(), securityObject);

            warnings.addAll(result.getWarnings());

            DayOfWeek deploymentDay = shift.getDeploymentDate().getDayOfWeek();
            boolean isRelevant = result.getConfiguration().getInspectionDays()
                    .map(days -> days.contains(deploymentDay))
                    .orElse(true);

            if (isRelevant) {
                result.getConfiguration().getOpeningTime().ifPresent(openingTime -> {
                    if (!isWithinShiftHours(openingTime, shift)) {
                        warnings.add(new Warning("Opening time " + openingTime
                                + " is outside shift hours"));
                    }
                });
                result.getConfiguration().getClosingTime().ifPresent(closingTime -> {
                    if (!isWithinShiftHours(closingTime, shift)) {
                        warnings.add(new Warning("Closing time " + closingTime
                                + " is outside shift hours"));
                    }
                });
                entries.add(new ChecklistEntry(securityObject, result.getConfiguration()));
            }
        }

        StandardConfiguration finalConfig = entries.isEmpty()
                ? new StandardConfiguration.Builder().build()
                : entries.get(0).getResolvedConfiguration();

        return new Checklist(1L, shift, finalConfig,
                LocalDateTime.now(), warnings, entries);
    }

    private boolean isWithinShiftHours(LocalTime time, Shift shift) {
        if (shift.isNightShift()) {
            return !time.isBefore(shift.getStartTime()) || !time.isAfter(shift.getEndTime());
        } else {
            return !time.isBefore(shift.getStartTime()) && !time.isAfter(shift.getEndTime());
        }
    }
}