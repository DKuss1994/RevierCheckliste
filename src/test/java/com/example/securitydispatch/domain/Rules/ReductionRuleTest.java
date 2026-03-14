package com.example.securitydispatch.domain.Rules;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ReductionRuleTest {

        @Test
        void shouldCreateReductionRuleWithInspectionCount() {
            ReductionRule rule = new ReductionRule.Builder(
                    LocalDate.of(2024, 3, 1),
                    LocalDate.of(2024, 4, 30))
                    .inspectionCount(1)
                    .inspectionDays(Set.of(DayOfWeek.MONDAY))
                    .build();

            assertThat(rule.getInspectionCount()).hasValue(1);
            assertThat(rule.getInspectionDays()).hasValue(Set.of(DayOfWeek.MONDAY));
        }

        @Test
        void shouldCreateReductionRuleWithRemoveClosing() {
            ReductionRule rule = new ReductionRule.Builder(
                    LocalDate.of(2024, 3, 1),
                    LocalDate.of(2024, 4, 30))
                    .removeClosing(true)
                    .build();

            assertThat(rule.isRemoveClosing()).isTrue();
        }

        @Test
        void shouldThrowExceptionWhenNoFieldIsSet() {
            assertThatThrownBy(() -> new ReductionRule.Builder(
                    LocalDate.of(2024, 3, 1),
                    LocalDate.of(2024, 4, 30))
                    .build())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("ReductionRule must have at least one field set");
        }
    }

