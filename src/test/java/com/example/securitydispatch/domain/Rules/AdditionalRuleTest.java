package com.example.securitydispatch.domain.Rules;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AdditionalRuleTest {
     @Test
    void shouldCreateAdditionalRuleWithInspectionCount(){
         AdditionalRule rule = new AdditionalRule.Builder(
                 LocalDate.of(2024,3,1),
                 LocalDate.of(2024,4,1))
                 .inspectionCount(2)
                 .inspectionDays(Set.of(DayOfWeek.MONDAY,DayOfWeek.FRIDAY))
                 .build();
         assertThat(rule.getInspectionCount()).hasValue(2);
         assertThat(rule.getInspectionDays()).hasValue(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));


     }
    @Test
    void shouldThrowExceptionWhenNoFieldIsSet() {
        assertThatThrownBy(() -> new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 30))
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("AdditionalRule must have at least one field set");
    }
    @Test
    void shouldCreateAdditionalRuleWithClosing() {
        AdditionalRule rule = new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 5, 31))
                .closingTime(LocalTime.of(22, 0))
                .closingDays(Set.of(DayOfWeek.MONDAY))
                .build();

        assertThat(rule.getClosingTime()).hasValue(LocalTime.of(22, 0));
        assertThat(rule.getClosingDays()).hasValue(Set.of(DayOfWeek.MONDAY));
    }
}
