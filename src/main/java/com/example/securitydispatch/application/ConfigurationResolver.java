package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.domain.Rules.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigurationResolver {
    public ResolutionResult resolve(LocalDate deploymentDate, SecurityObject securityObject) {
        List<Warning> warnings = new ArrayList<>();
        // Schritt 1: Orignal merken
        StandardConfiguration original = securityObject.getStandardConfiguration();
        StandardConfiguration base = original;
        // Schritt 2: Override anwenden
        for (OverrideRule rule : securityObject.getOverrideRules()) {
            if (rule.isActive(deploymentDate)) {
                base = rule.getConfiguration();
            }
        }
        // Schritt 3: Felder berechnen — Override hat Vorrang, sonst Standard
        int inspectionCount = base.getInspectionCount()
                .orElse(original.getInspectionCount().orElse(0));
        LocalTime closingTime = base.getClosingTime()
                .orElse(original.getClosingTime().orElse(null));
        Set<DayOfWeek> closingDays = base.getClosingDays()
                .orElse(original.getClosingDays().orElse(null));
        LocalTime openingTime = base.getOpeningTime()
                .orElse(original.getOpeningTime().orElse(null));
        Set<DayOfWeek> openingDays = base.getOpeningDays()
                .orElse(original.getOpeningDays().orElse(null));
        Set<DayOfWeek> inspectionDays = base.getInspectionDays()
                .orElse(original.getInspectionDays().orElse(null));

        // Schritt 4: Additional addieren
        for (AdditionalRule rule : securityObject.getAdditionalRules()) {
            if (rule.isActive(deploymentDate)) {
                inspectionCount += rule.getInspectionCount().orElse(0);
            }
        }
        // Additional: closingTime und closingDays
        LocalTime additionalClosingTime = closingTime;
        Set<DayOfWeek> additionalClosingDays = closingDays;
        for (AdditionalRule rule : securityObject.getAdditionalRules()) {
            if (rule.isActive(deploymentDate)) {
                additionalClosingTime = rule.getClosingTime().orElse(additionalClosingTime);
                additionalClosingDays = rule.getClosingDays().orElse(additionalClosingDays);
            }
        }
        //Additional: openingTime and openingDays
        LocalTime additionalOpeningTime = openingTime;
        Set<DayOfWeek> additionalOpeningDays = openingDays;
        for (AdditionalRule rule : securityObject.getAdditionalRules()) {
            if (rule.isActive(deploymentDate)) {
                additionalOpeningTime = rule.getOpeningTime().orElse(additionalOpeningTime);
                additionalOpeningDays = rule.getOpeningDays().orElse(additionalOpeningDays);
            }
        }
        // Schritt 5: Reduction abziehen
        for (ReductionRule rule : securityObject.getReductionRules()) {
            if (rule.isActive(deploymentDate)) {
                inspectionCount -= rule.getInspectionCount().orElse(0);
            }

        }
        // Reduction: removeClosing
        if (securityObject.getReductionRules().stream()
                .anyMatch(rule -> rule.isActive(deploymentDate) && rule.isRemoveClosing())) {
            additionalClosingTime = null;
            additionalClosingDays = null;
        }

// Reduction: removeOpening
        if (securityObject.getReductionRules().stream()
                .anyMatch(rule -> rule.isActive(deploymentDate) && rule.isRemoveOpening())) {
            additionalOpeningTime = null;
            additionalOpeningDays = null;
        }

        // Schritt 6: Warnung wenn negativ
        if (inspectionCount < 0) {
            warnings.add(new Warning("Inspection count cannot be negative, set to 0"));
            inspectionCount = 0;
        }
        // Schritt 7: Finale Config bauen
        StandardConfiguration finalConfig = new StandardConfiguration.Builder()
                .inspectionCount(inspectionCount)
                .inspectionDays(inspectionDays)
                .openingTime(additionalOpeningTime)
                .openingDays(additionalOpeningDays)
                .closingTime(additionalClosingTime)
                .closingDays(additionalClosingDays)
                .build();

        return new ResolutionResult(finalConfig, warnings);

    }
}
