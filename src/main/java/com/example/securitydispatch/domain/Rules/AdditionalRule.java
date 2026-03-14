package com.example.securitydispatch.domain.Rules;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

public class AdditionalRule extends Rule{
    private final Integer inspectionCount;
    private final Set<DayOfWeek> inspectionDays;
    private final LocalTime openingTime;
    private final Set<DayOfWeek> openingDays;
    private final LocalTime closingTime;
    private final Set<DayOfWeek> closingDays;



    public AdditionalRule(Builder builder) {
        super(builder.startDate, builder.endDate);
        this.inspectionCount = builder.inspectionCount;
        this.inspectionDays = builder.inspectionDays;
        this.openingTime = builder.openingTime;
        this.openingDays = builder.openingDays;
        this.closingTime = builder.closingTime;
        this.closingDays = builder.closingDays;
    }
    public Optional<Integer> getInspectionCount() {
        return Optional.ofNullable(inspectionCount);
    }

    public Optional<LocalTime>getOpeningTime() {
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

    public Optional<Set<DayOfWeek>> getInspectionDays() {
        return Optional.ofNullable(inspectionDays);
    }

    public static class Builder {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private  Integer inspectionCount;
        private  Set<DayOfWeek> inspectionDays;
        private  LocalTime openingTime;
        private  Set<DayOfWeek> openingDays;
        private LocalTime closingTime;
        private  Set<DayOfWeek> closingDays;
        public Builder(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
        public Builder inspectionCount(int count){
            this.inspectionCount = count;
            return this;
        }
        public Builder inspectionDays(Set <DayOfWeek> days){
            this.inspectionDays = days;
            return this;
        }
        public Builder openingTime (LocalTime openingTime){
            this.openingTime = openingTime;
            return this;
        }
        public Builder closingTime (LocalTime closingTime){
            this.closingTime = closingTime;
            return this;
        }
        public Builder closingDays (Set <DayOfWeek> days){
            this.closingDays = days;
            return this;
        }
        public Builder openingDays (Set <DayOfWeek>  days){
            this.openingDays = days;
            return this;
        }
        public AdditionalRule build(){
            if (inspectionCount == null && inspectionDays == null &&
                    openingTime == null && openingDays == null &&
                    closingTime == null && closingDays == null){
                throw new IllegalArgumentException("AdditionalRule must have at least one field set");
            }
            return new AdditionalRule(this);
        }
    }
}
