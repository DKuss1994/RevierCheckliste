package com.example.securitydispatch.infrastructure.persistence;
import jakarta.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
@Embeddable
public class StandardConfigurationEmbeddable {
    private Integer inspectionCount;
    private String inspectionDays; // z.B. "MONDAY,FRIDAY"
    private LocalTime openingTime;
    private String openingDays;
    private LocalTime closingTime;
    private String closingDays;
    private LocalTime inspectionWindowStart;
    private LocalTime inspectionWindowEnd;
    private String notes;

    protected StandardConfigurationEmbeddable() {}

    public StandardConfigurationEmbeddable(Integer inspectionCount,
                                           Set<DayOfWeek> inspectionDays,
                                           LocalTime openingTime,
                                           Set<DayOfWeek> openingDays,
                                           LocalTime closingTime,
                                           Set<DayOfWeek> closingDays,
                                           LocalTime inspectionWindowStart,
                                           LocalTime inspectionWindowEnd,
                                           String notes) {
        this.inspectionCount = inspectionCount;
        this.inspectionDays = daysToString(inspectionDays);
        this.openingTime = openingTime;
        this.openingDays = daysToString(openingDays);
        this.closingTime = closingTime;
        this.closingDays = daysToString(closingDays);
        this.inspectionWindowStart = inspectionWindowStart;
        this.inspectionWindowEnd = inspectionWindowEnd;
        this.notes = notes;
    }
    // Set<DayOfWeek> → "MONDAY,FRIDAY"
    String daysToString(Set<DayOfWeek> days) {
        if (days == null) return null;
        return days.stream()
                .map(DayOfWeek::name)
                .collect(Collectors.joining(","));
    }

    // "MONDAY,FRIDAY" → Set<DayOfWeek>
    Set<DayOfWeek> stringToDays(String days) {
        if (days == null) return null;
        return Arrays.stream(days.split(","))
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet());
    }

    public Integer getInspectionCount() {
        return inspectionCount;
    }

    public Set<DayOfWeek> getInspectionDays() {
        return stringToDays(inspectionDays);
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public String getOpeningDays() {
        return openingDays;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public String getClosingDays() {
        return closingDays;
    }

    public LocalTime getInspectionWindowStart() {
        return inspectionWindowStart;
    }

    public LocalTime getInspectionWindowEnd() {
        return inspectionWindowEnd;
    }

    public String getNotes() {
        return notes;
    }
}
