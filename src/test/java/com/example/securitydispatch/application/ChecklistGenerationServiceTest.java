package com.example.securitydispatch.application;
import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.domain.Rules.OverrideRule;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Test
    void shouldGenerateChecklistWithStandardConfigWhenNoRulesAreActive() {
        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2024, 3, 11),
                LocalTime.of(6, 0), LocalTime.of(14, 0));

        Checklist checklist = service.generate(shift, securityObject);

        assertThat(checklist.getConfig().getInspectionCount()).hasValue(2);
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

        assertThat(checklist.getConfig().getInspectionCount()).hasValue(3);
    }
}
