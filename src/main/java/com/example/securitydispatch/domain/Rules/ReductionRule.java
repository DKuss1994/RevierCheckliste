package com.example.securitydispatch.domain.Rules;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class ReductionRule extends Rule {

    private final Integer inspectionCount;
    private final Set<DayOfWeek> inspectionDays;
    private final boolean removeOpening;
    private final boolean removeClosing;

    private ReductionRule(Builder builder) {
        super(builder.startDate, builder.endDate);
        this.inspectionCount = builder.inspectionCount;
        this.inspectionDays = builder.inspectionDays;
        this.removeOpening = builder.removeOpening;
        this.removeClosing = builder.removeClosing;
    }

    public Optional<Integer> getInspectionCount() {
        return Optional.ofNullable(inspectionCount);
    }

    public Optional<Set<DayOfWeek>> getInspectionDays() {
        return Optional.ofNullable(inspectionDays);
    }

    public boolean isRemoveOpening() {
        return removeOpening;
    }

    public boolean isRemoveClosing() {
        return removeClosing;
    }

    public static class Builder {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private Integer inspectionCount;
        private Set<DayOfWeek> inspectionDays;
        private boolean removeOpening;
        private boolean removeClosing;

        public Builder(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Builder inspectionCount(int count) {
            this.inspectionCount = count;
            return this;
        }

        public Builder inspectionDays(Set<DayOfWeek> days) {
            this.inspectionDays = days;
            return this;
        }

        public Builder removeOpening(boolean remove) {
            this.removeOpening = remove;
            return this;
        }

        public Builder removeClosing(boolean remove) {
            this.removeClosing = remove;
            return this;
        }

        public ReductionRule build() {
            if (inspectionCount == null && inspectionDays == null
                    && !removeOpening && !removeClosing) {
                throw new IllegalArgumentException("ReductionRule must have at least one field set");
            }
            return new ReductionRule(this);
        }
    }
}