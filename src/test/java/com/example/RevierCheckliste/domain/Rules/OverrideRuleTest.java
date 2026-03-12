package com.example.RevierCheckliste.domain.Rules;
import com.example.RevierCheckliste.domain.StandardConfiguration;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OverrideRuleTest {
    @Test
    void shouldCreateOverrideRuleWithDateRangeAndConfiguration() {
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(3)
                .build();
        OverrideRule overrideRule = new OverrideRule(
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 15),config);
        assertThat(overrideRule.getStartDay()).isEqualTo(LocalDate.of(2026, 3, 1));
        assertThat(overrideRule.getEndDay()).isEqualTo(LocalDate.of(2026, 3, 15));
        assertThat(overrideRule.getConfiguration()).isEqualTo(config);
    }
    @Test
    void shouldThrowExceptionWhenConfigurationIsNull(){
        assertThatThrownBy(()->new OverrideRule(
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 15),null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("OverrideRule configuration must not be null");
    }
}