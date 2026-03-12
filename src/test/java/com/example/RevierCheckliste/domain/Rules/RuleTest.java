package com.example.RevierCheckliste.domain.Rules;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
public class RuleTest {
    @Test
    void shouldCreateRuleWithStartAndEndTime(){
        Rule rule = new Rule(LocalDate.of(2026,3,12),LocalDate.of(2026,4,12));
        assertThat(rule.getStartDay()).isEqualTo(LocalDate.of(2026,3,12));
        assertThat(rule.getEndDay()).isEqualTo(LocalDate.of(2026,4,12));

    }
    @Test
    void shouldThrowExceptionStartDayIsNull(){
        assertThatThrownBy(()->new Rule(null,LocalDate.of(2026,4,12)))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Rule start day must not be null");}
    @Test
    void shouldThrowExceptionEndDayIsNull(){
        assertThatThrownBy(()->new Rule(LocalDate.of(2026,3,12),null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Rule end day must not be null");
    }@Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate(){
        assertThatThrownBy(()->new Rule(LocalDate.of(2026,5,12),LocalDate.of(2026,4,12)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rule start day must not be after end day");

    }
    @Test
    void shouldDetectWhenDateIsWithinRule() {
        Rule rule = new Rule(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));

        assertThat(rule.isActive(LocalDate.of(2024, 6, 15))).isTrue();
    }

    @Test
    void shouldDetectWhenDateIsOutsideRule() {
        Rule rule = new Rule(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));

        assertThat(rule.isActive(LocalDate.of(2025, 1, 1))).isFalse();
    }
}
