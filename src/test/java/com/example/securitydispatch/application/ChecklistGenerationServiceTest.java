package com.example.securitydispatch.application;
import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.domain.Rules.AdditionalRule;
import com.example.securitydispatch.domain.Rules.OverrideRule;
import com.example.securitydispatch.domain.Rules.ReductionRule;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
public class ChecklistGenerationServiceTest {

    private final Zone zone = new Zone(1L, "Zone 1");
    private final Address address = new Address("Musterstraße 1", "Berlin", "10115");
    private final Driver driver = new Driver(1L, "Max", "Mustermann");
    private final StandardConfiguration standardConfig = new StandardConfiguration.Builder()
            .inspectionCount(2)
            .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
            .build();
    private final SecurityObject securityObject = new SecurityObject(
            1L, "Object A", zone, address, standardConfig);

    private final ChecklistGenerationService service = new ChecklistGenerationService();
    private final Shift shift = new Shift(1L, driver, zone,
            LocalDate.of(2024, 3, 11),//Monday
            LocalTime.of(6, 0), LocalTime.of(14, 0));
    @Test
    void shouldGenerateChecklistWithStandardConfigWhenNoRulesAreActive() {


        Checklist checklist = service.generate(shift, securityObject);

        assertThat(checklist.getConfiguration().getInspectionCount()).hasValue(2);
    }
    @Test
    void shouldApplyOverrideRuleWhenActive() {
        StandardConfiguration overrideConfig = new StandardConfiguration.Builder()
                .inspectionCount(3)
                .build();

        OverrideRule rule = new OverrideRule(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31),
                overrideConfig);

        securityObject.addOverrideRule(rule);

        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2024, 3, 11),
                LocalTime.of(6, 0), LocalTime.of(14, 0));

        Checklist checklist = service.generate(shift, securityObject);

        assertThat(checklist.getConfiguration().getInspectionCount()).hasValue(3);
    }
    @Test
    void shouldApplyAdditionalRuleWhenActive(){
        AdditionalRule rule = new AdditionalRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31)
        ).inspectionCount(3)
                .build();
        securityObject.addAdditionalRule(rule);
        Checklist checklist = service.generate(shift,securityObject);
        // Standard 2 + Additional 3 = 5
        assertThat(checklist.getConfiguration().getInspectionCount()).hasValue(5);
    }
    @Test
    void shouldApplyReductionRuleWhenActive(){
        ReductionRule rule = new ReductionRule.Builder(
                LocalDate.of(2024,2,2),
                LocalDate.of(2024, 3, 31)
        ).inspectionCount(2)
                .build();
        securityObject.addReductionRule(rule);
        Checklist checklist = service.generate(shift,securityObject);
        // Standard 2 - Reduction 2 =0
        assertThat(checklist.getConfiguration().getInspectionCount()).hasValue(0);
    }
    @Test
    void shouldReturnWarningsWhenInspectionCountIsNegative() {
        ReductionRule rule = new ReductionRule.Builder(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 31))
                .inspectionCount(5)
                .build();

        securityObject.addReductionRule(rule);

        Checklist checklist = service.generate(shift, securityObject);

        assertThat(checklist.getWarnings()).hasSize(1);
        assertThat(checklist.getWarnings().get(0).getMessage())
                .isEqualTo("Inspection count cannot be negative, set to 0");
    }

    @Test
    void shouldReturnNoWarningsWhenEverythingIsValid() {
        Checklist checklist = service.generate(shift, securityObject);

        assertThat(checklist.getWarnings()).isEmpty();
    }
    @Test
    void shouldWarnWhenOpeningTimeIsOutsideShift(){
        StandardConfiguration config = new StandardConfiguration.Builder()
                .openingTime(LocalTime.of(7, 0)) // außerhalb der Schicht!
                .openingDays(Set.of(DayOfWeek.MONDAY))
                .build();


        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2024, 3, 11),
                LocalTime.of(22, 0), LocalTime.of(6, 0)); // Nachtschicht

        SecurityObject securityObject = new SecurityObject(
                1L, "Object A", zone,
                new Address("Musterstraße 1", "Berlin", "10115"),
                config);
        Checklist checklist = service.generate(shift,securityObject);
        assertThat(checklist.getWarnings()).hasSize(1);
        assertThat(checklist.getWarnings().getFirst().getMessage())
                .isEqualTo("Opening time 07:00 is outside shift hours");

    } @Test
    void shouldWarnWhenClosingTimeIsOutsideShift(){
        StandardConfiguration config = new StandardConfiguration.Builder()
                .closingTime(LocalTime.of(7, 0)) // außerhalb der Schicht!
                .closingDays(Set.of(DayOfWeek.MONDAY))
                .build();


        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2024, 3, 11),
                LocalTime.of(22, 0), LocalTime.of(6, 0)); // Nachtschicht

        SecurityObject securityObject = new SecurityObject(
                1L, "Object A", zone,
                new Address("Musterstraße 1", "Berlin", "10115"),
                config);
        Checklist checklist = service.generate(shift,securityObject);
        assertThat(checklist.getWarnings()).hasSize(1);
        assertThat(checklist.getWarnings().getFirst().getMessage())
                .isEqualTo("Closing time 07:00 is outside shift hours");

    }
    @Test
    void shouldGenerateChecklistWithEntriesForAllObjectsInZone(){
        StandardConfiguration configA = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .inspectionDays(Set.of(DayOfWeek.MONDAY))
                .build();

        StandardConfiguration configB = new StandardConfiguration.Builder()
                .inspectionCount(1)
                .inspectionDays(Set.of(DayOfWeek.FRIDAY)) // nicht Montag!
                .build();

        SecurityObject objectA = new SecurityObject(1L, "Object A", zone, address, configA);
        SecurityObject objectB = new SecurityObject(2L, "Object B", zone, address, configB);

        Checklist checklist = service.generate(shift, List.of(objectA, objectB));
assertThat(checklist.getEntries()).hasSize(1);
assertThat(checklist.getEntries().getFirst().getSecurityObject().getName()).isEqualTo("Object A");
    }
}
