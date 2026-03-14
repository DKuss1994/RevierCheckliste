package com.example.securitydispatch.domain;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
public class StandardConfigurationTest {
    @Test
    void shouldCreateEmptyStandardConfiguration(){
        StandardConfiguration config = new StandardConfiguration.Builder().build();
        assertThat(config.getInspectionCount()).isEmpty();
        assertThat(config.getInspectionDays()).isEmpty();
        assertThat(config.getOpeningTime()).isEmpty();
        assertThat(config.getOpeningDays()).isEmpty();
        assertThat(config.getClosingTime()).isEmpty();
        assertThat(config.getClosingDays()).isEmpty();
        assertThat(config.getNotes()).isEmpty();
    }
    @Test
    void shouldCreateConfigurationWithInspectionOnly(){
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
                .build();
        assertThat(config.getInspectionCount()).hasValue(2);
        assertThat(config.getInspectionDays()).hasValue(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));

    }
    @Test
    void shouldCreateConfigurationWithClosingOnly() {
        StandardConfiguration config = new StandardConfiguration.Builder()
                .closingTime(LocalTime.of(22, 0))
                .closingDays(Set.of(DayOfWeek.MONDAY))
                .build();

        assertThat(config.getClosingTime()).hasValue(LocalTime.of(22, 0));
        assertThat(config.getClosingDays()).hasValue(Set.of(DayOfWeek.MONDAY));
    }
    @Test
    void shouldCreateConfigurationWithInspectionWindow() {
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionWindowStart(LocalTime.of(8, 0))
                .inspectionWindowEnd(LocalTime.of(20, 0))
                .build();

        assertThat(config.getInspectionWindowStart()).hasValue(LocalTime.of(8, 0));
        assertThat(config.getInspectionWindowEnd()).hasValue(LocalTime.of(20, 0));
    }
}
