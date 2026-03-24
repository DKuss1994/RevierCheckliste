package com.example.securitydispatch.infrastructure.persistence;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

class StandardConfigurationEmbeddableTest {

    @Test
    void shouldConvertDaysOfWeekToStringAndBack() {
        Set<DayOfWeek> days = Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);

        StandardConfigurationEmbeddable embeddable = new StandardConfigurationEmbeddable(
                2, days, null, null, null, null, null, null, null);


        assertThat(embeddable.getInspectionDays())
                .contains(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)
                .hasSize(2);
    }

    @Test
    void shouldHandleNullDays() {
        StandardConfigurationEmbeddable embeddable = new StandardConfigurationEmbeddable(
                2, null, null, null, null, null, null, null, null);

        assertThat(embeddable.getInspectionDays()).isNull();
    }
    @Test
    void shouldPersistAndRetrieveDaysCorrectly() {
        Set<DayOfWeek> days = Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        StandardConfigurationEmbeddable embeddable = new StandardConfigurationEmbeddable(
                2, days, null, null, null, null, null, null, null);

        // Simuliere Persistenz: Conversion in String
        String dbValue = embeddable.daysToString(embeddable.getInspectionDays());
        Set<DayOfWeek> restored = embeddable.stringToDays(dbValue);

        assertThat(restored).containsExactlyInAnyOrder(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
    }
}
