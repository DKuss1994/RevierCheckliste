package com.example.securitydispatch.application;
import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.domain.Rules.OverrideRule;
import com.example.securitydispatch.domain.Rules.ReductionRule;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
public class ConfigurationResolverTest {
    private final Zone zone = new Zone(1L, "Zone 1");
    private final Address address = new Address("Musterstraße 1", "Berlin", "10115");
    private final StandardConfiguration standardConfig = new StandardConfiguration.Builder()
            .inspectionCount(2)
            .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
            .build();
    private final SecurityObject securityObject = new SecurityObject(
            1L, "Object A", zone, address, standardConfig);

    private final ConfigurationResolver resolver = new ConfigurationResolver();
    @Test
    void shouldReturnStandardConfigurationWhenNoRulesAreActive(){
        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);
    assertThat(result.getConfiguration().getInspectionCount()).hasValue(2);
    assertThat(result.getWarnings()).isEmpty();
    }
    @Test
    void shouldApplyOverrideRuleWhenActive() {
        StandardConfiguration overrideConfig = new StandardConfiguration.Builder()
                .inspectionCount(3)
                .build();

        securityObject.addOverrideRule(new OverrideRule(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31),
                overrideConfig));

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);

        assertThat(result.getConfiguration().getInspectionCount()).hasValue(3);
        assertThat(result.getWarnings()).isEmpty();
    }

    @Test
    void shouldWarnWhenInspectionCountIsNegative() {
        securityObject.addReductionRule(new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .inspectionCount(5)
                .build());

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);

        assertThat(result.getConfiguration().getInspectionCount()).hasValue(0);
        assertThat(result.getWarnings()).hasSize(1);
    }
}
