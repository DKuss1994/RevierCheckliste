package com.example.securitydispatch.application;

import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.domain.Rules.*;
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
            .closingTime(LocalTime.of(22, 0))
            .closingDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
            .build();
    private final SecurityObject securityObject = new SecurityObject(
            1L, "Object A", zone, address, standardConfig);

    private final ConfigurationResolver resolver = new ConfigurationResolver();

    @Test
    void shouldReturnStandardConfigurationWhenNoRulesAreActive() {
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

    @Test
    void shouldKeepStandardFieldsNotOverriddenByOverrideRule() {
        StandardConfiguration overrideConfig = new StandardConfiguration.Builder()
                .inspectionCount(3)
                .build(); // closingTime nicht gesetzt!

        securityObject.addOverrideRule(new OverrideRule(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31),
                overrideConfig));

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);

        assertThat(result.getConfiguration().getInspectionCount()).hasValue(3);
        assertThat(result.getConfiguration().getClosingTime()).hasValue(LocalTime.of(22, 0));
    }

    @Test
    void shouldApplyAdditionalRuleInspectionCount() {
        securityObject.addAdditionalRule(new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .inspectionCount(2)
                .build());

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);

        // Standard 2 + Additional 2 = 4
        assertThat(result.getConfiguration().getInspectionCount()).hasValue(4);
        assertThat(result.getWarnings()).isEmpty();
    }

    @Test
    void shouldApplyAdditionalRuleClosingTime() {
        SecurityObject objectWithoutClosing = new SecurityObject(
                2L, "Object B", zone, address,
                new StandardConfiguration.Builder()
                        .inspectionCount(2)
                        .build()); // kein closingTime!

        objectWithoutClosing.addAdditionalRule(new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .closingTime(LocalTime.of(23, 0))
                .closingDays(Set.of(DayOfWeek.TUESDAY))
                .build());

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), objectWithoutClosing);

        assertThat(result.getConfiguration().getClosingTime()).hasValue(LocalTime.of(23, 0));
        assertThat(result.getWarnings()).isEmpty(); }

    @Test
    void shouldApplyReductionRuleRemoveClosing() {
        securityObject.addReductionRule(new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .removeClosing(true)
                .build());

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);

        assertThat(result.getConfiguration().getClosingTime()).isEmpty();
        assertThat(result.getConfiguration().getClosingDays()).isEmpty();
    }

    @Test
    void shouldApplyReductionRuleRemoveOpening() {
        securityObject.addReductionRule(new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .removeOpening(true)
                .build());

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);

        assertThat(result.getConfiguration().getOpeningTime()).isEmpty();
        assertThat(result.getConfiguration().getOpeningDays()).isEmpty();
    }

    @Test
    void shouldWarningWhenRemovingClosingTimeDoesNotExist() {
        SecurityObject securityObjectWithoutClosing = new SecurityObject
                (2L, "Object B", zone, address, new StandardConfiguration.Builder()
                        .inspectionCount(2)
                        .build());
        securityObjectWithoutClosing.addReductionRule(new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .removeClosing(true)
                .build());
        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024,3,15),securityObjectWithoutClosing
        );
        assertThat(result.getWarnings()).hasSize(1);
        assertThat(result.getWarnings().get(0).getMessage()).isEqualTo
                ("Cannot remove closing time that does not exist");
    }
    @Test
    void shouldWarningWhenRemovingOpeningTimeDoesNotExist() {
        SecurityObject securityObjectWithoutClosing = new SecurityObject
                (2L, "Object B", zone, address, new StandardConfiguration.Builder()
                        .inspectionCount(2)
                        .build());
        securityObjectWithoutClosing.addReductionRule(new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .removeOpening(true)
                .build());
        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024,3,15),securityObjectWithoutClosing
        );
        assertThat(result.getWarnings()).hasSize(1);
        assertThat(result.getWarnings().get(0).getMessage()).isEqualTo
                ("Cannot remove opening time that does not exist");
    }
    @Test
    void shouldWarnWhenAddingClosingTimeThatAlreadyExists() {
        securityObject.addAdditionalRule(new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .closingTime(LocalTime.of(23, 0))
                .closingDays(Set.of(DayOfWeek.TUESDAY))
                .build());

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), securityObject);

        assertThat(result.getWarnings()).hasSize(1);
        assertThat(result.getWarnings().get(0).getMessage())
                .isEqualTo("Cannot add closing time that already exists");
    }

    @Test
    void shouldWarnWhenAddingOpeningTimeThatAlreadyExists() {
        StandardConfiguration configWithOpening = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .openingTime(LocalTime.of(8, 0))
                .openingDays(Set.of(DayOfWeek.MONDAY))
                .build();

        SecurityObject objectWithOpening = new SecurityObject(
                2L, "Object B", zone, address, configWithOpening);

        objectWithOpening.addAdditionalRule(new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .openingTime(LocalTime.of(9, 0))
                .openingDays(Set.of(DayOfWeek.TUESDAY))
                .build());

        ResolutionResult result = resolver.resolve(
                LocalDate.of(2024, 3, 11), objectWithOpening);

        assertThat(result.getWarnings()).hasSize(1);
        assertThat(result.getWarnings().get(0).getMessage())
                .isEqualTo("Cannot add opening time that already exists");
    }

}