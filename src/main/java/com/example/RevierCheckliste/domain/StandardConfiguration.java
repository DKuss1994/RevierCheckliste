package com.example.RevierCheckliste.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

public class StandardConfiguration {
    private final Integer inspectionCount;
    private final Set<DayOfWeek> inspectionDays;
    private final LocalTime openingTime;
    private final Set<DayOfWeek> openingDays;
    private final LocalTime closingTime;
    private final Set<DayOfWeek> closingDays;
    private final LocalTime inspectionWindowStart;
    private final LocalTime inspectionWindowEnd;
    private final String notes;

    private StandardConfiguration(Builder builder) {
        this.inspectionCount = builder.inspectionCount;
        this.inspectionDays = builder.inspectionDays;
        this.openingTime = builder.openingTime;
        this.openingDays = builder.openingDays;
        this.closingTime = builder.closingTime;
        this.closingDays = builder.closingDays;
        this.inspectionWindowStart = builder.inspectionWindowStart;
        this.inspectionWindowEnd = builder.inspectionWindowEnd;
        this.notes = builder.notes;
    }

    public Optional<Integer> getInspectionCount() {
        return Optional.ofNullable(inspectionCount);
    }

    public Optional<Set<DayOfWeek>> getInspectionDays() {
        return Optional.ofNullable(inspectionDays);
    }

    public Optional<LocalTime> getOpeningTime() {
        return Optional.ofNullable(openingTime);
    }

    public Optional<Set<DayOfWeek>> getOpeningDays() {
        return Optional.ofNullable(openingDays);
    }

    public Optional<LocalTime> getClosingTime() {
        return Optional.ofNullable(closingTime);
    }

    public Optional<Set<DayOfWeek>> getClosingDays() {
        return Optional.ofNullable(closingDays);
    }

    public Optional<LocalTime> getInspectionWindowStart() {
        return Optional.ofNullable(inspectionWindowStart);
    }

    public Optional<LocalTime> getInspectionWindowEnd() {
        return Optional.ofNullable(inspectionWindowEnd);
    }

    public Optional<String> getNotes() {
        return Optional.ofNullable(notes);
    }

    public static class Builder {
        private Integer inspectionCount;
        private Set<DayOfWeek> inspectionDays;
        private LocalTime openingTime;
        private Set<DayOfWeek> openingDays;
        private LocalTime closingTime;
        private Set<DayOfWeek> closingDays;
        private LocalTime inspectionWindowStart;
        private LocalTime inspectionWindowEnd;
        private String notes;


        public Builder inspectionCount(int count) {
            this.inspectionCount = count;
            return this;
        }

        public Builder inspectionDays(Set<DayOfWeek> days) {
            this.inspectionDays = days;
            return this;
        }

        public Builder openingTime(LocalTime time) {
            this.openingTime = time;
            return this;
        }

        public Builder openingDays(Set<DayOfWeek> days) {
            this.openingDays = days;
            return this;
        }

        public Builder closingTime(LocalTime time) {
            this.closingTime = time;
            return this;
        }

        public Builder closingDays(Set<DayOfWeek> days) {
            this.closingDays = days;
            return this;
        }

        public Builder inspectionWindowStart(LocalTime time) {
            this.inspectionWindowStart = time;
            return this;
        }

        public Builder inspectionWindowEnd(LocalTime time) {
            this.inspectionWindowEnd = time;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public StandardConfiguration build() {
            return new StandardConfiguration(this);
        }


    }
}


