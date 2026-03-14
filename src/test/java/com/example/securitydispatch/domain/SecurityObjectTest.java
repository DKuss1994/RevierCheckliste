package com.example.securitydispatch.domain;

import com.example.securitydispatch.domain.Rules.AdditionalRule;
import com.example.securitydispatch.domain.Rules.OverrideRule;
import com.example.securitydispatch.domain.Rules.ReductionRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SecurityObjectTest {
    private final Zone zone = new Zone(1L, "Zone1");
    private final Address address = new Address("Musterstraße 1", "Berilin", "10115");
    private final StandardConfiguration config = new StandardConfiguration.Builder()
            .inspectionCount(2)
            .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
            .build();
    private final SecurityObject object = new SecurityObject
            (1L, "Object", zone, address, config);


    @Test
    void shouldCreateSecurityObjectWithIdNameAndZone() {
        SecurityObject securityObject = new SecurityObject(1L, "Object", zone, address, config);
        assertThat(securityObject.getAddress()).isEqualTo(address);
        assertThat(securityObject.getZone()).isEqualTo(zone);
        assertThat(securityObject.getName()).isEqualTo("Object");
        assertThat(securityObject.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenObjectIsBlankOrNull() {
        assertThatThrownBy(() -> new SecurityObject(1L, "      ", zone, address, config))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Object name must not be blank");
        assertThatThrownBy(() -> new SecurityObject(1L, null, zone, address, config))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Object name must not be blank");
    }

    @Test
    void shouldThrowExceptionWhenZoneIsNull() {
        assertThatThrownBy(() -> new SecurityObject(1L, "Object", null, address, config))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone name must not be null");
    }

    @Test
    void shouldThrowExceptionWhenAddressIsNull() {
        assertThatThrownBy(() -> new SecurityObject(1L, "Object", zone, null, config))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Address name must not be null");
    }

    @Test
    void shouldCreateSecurityObjectWithStandardConfiguration() {
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
                .build();
        SecurityObject object = new SecurityObject(1L, "Object", zone, address, config);
        assertThat(object.getStandardConfiguration()).isEqualTo(config);
    }

    @Test
    void shouldThrowExceptionWhenConfigIsNull() {
        assertThatThrownBy(() -> new SecurityObject(1L, "Object", zone, address, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Configuration must not be null");
    }

    @Test
    void shouldAddOverrideRuleToSecurityObject() {
        OverrideRule rule = new OverrideRule(LocalDate.of(2026, 3, 1)
                , LocalDate.of(2026, 3, 15), config);
        object.addOverrideRule(rule);
        assertThat(object.getOverrideRules()).containsExactly(rule);
    }

    @Test
    void shouldThrowExceptionWhenOverrideRulesOverlap() {
        OverrideRule rule1 = new OverrideRule(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 6, 30),
                config);

        OverrideRule rule2 = new OverrideRule(
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 12, 31),
                config);

        object.addOverrideRule(rule1);

        assertThatThrownBy(() -> object.addOverrideRule(rule2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Override rules must not overlap");
    }

    @Test
    void shouldAddAdditionalRuleToSecurityObject() {
        AdditionalRule rule = new AdditionalRule.Builder(LocalDate.of(2024, 01, 01)
                , LocalDate.of(2024, 2, 1))
                .inspectionCount(2)
                .inspectionDays(Set.of(DayOfWeek.MONDAY))
                .build();
        object.addAdditionalRule(rule);
        assertThat(object.getAdditionalRules()).containsExactly(rule);

    }

    @Test
    void shouldThrowExceptionWhenAdditionalRuleOfSameTypeOverlap() {
        AdditionalRule rule = new AdditionalRule.Builder(LocalDate.of(2024, 01, 01)
                , LocalDate.of(2024, 2, 1))
                .inspectionCount(2)
                .build();
        AdditionalRule rule2 = new AdditionalRule.Builder(LocalDate.of(2024, 01, 01)
                , LocalDate.of(2024, 1, 15))
                .inspectionCount(2)
                .build();
        object.addAdditionalRule(rule);

        assertThatThrownBy(() -> object.addAdditionalRule(rule2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Additional rules of same type must not overlap");


        }
    @Test
    void shouldAllowAdditionalRulesOfDifferentTypeToOverlap() {
        AdditionalRule rule1 = new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 30))
                .inspectionCount(2)
                .build();

        AdditionalRule rule2 = new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 5, 31))
                .closingTime(LocalTime.of(22, 0))
                .closingDays(Set.of(DayOfWeek.MONDAY))
                .build();

        object.addAdditionalRule(rule1);

        Assertions.assertDoesNotThrow(() -> object.addAdditionalRule(rule2));
    }
    @Test
    void shouldAddReductionRuleToSecurityObject() {
        ReductionRule rule = new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 30))
                .inspectionCount(1)
                .build();

        object.addReductionRule(rule);

        assertThat(object.getReductionRules()).containsExactly(rule);
    }

    @Test
    void shouldThrowExceptionWhenReductionRulesOfSameTypeOverlap() {
        ReductionRule rule1 = new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 30))
                .inspectionCount(1)
                .build();

        ReductionRule rule2 = new ReductionRule.Builder(
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 5, 31))
                .inspectionCount(1)
                .build();

        object.addReductionRule(rule1);

        assertThatThrownBy(() -> object.addReductionRule(rule2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Reduction rules of same type must not overlap");
    }
}
