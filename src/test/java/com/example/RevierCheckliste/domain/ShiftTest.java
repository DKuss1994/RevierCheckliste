package com.example.RevierCheckliste.domain;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class ShiftTest {
    private final Zone zone = new Zone(1L, "Zone 1");
    private final Address address = new Address("Musterstraße 1", "Berlin", "10115");
    private final StandardConfiguration config = new StandardConfiguration.Builder().build();
    private final SecurityObject object = new SecurityObject(1L, "Object A", zone, address, config);
    private final Driver driver = new Driver(1L, "Max", "Mustermann");
    @Test
    void shouldCreateShiftWithAllFields(){
        Shift shift = new Shift(1L,driver,zone,LocalDate.of(2024,3,11),
                LocalTime.of(6,0),LocalTime.of(14,0));
        assertThat(shift.getId()).isEqualTo(1L);
        assertThat(shift.getDriver()).isEqualTo(driver);
        assertThat(shift.getZone()).isEqualTo(zone);
        assertThat(shift.getDeploymentDate()).isEqualTo(LocalDate.of(2024,3,11));
        assertThat(shift.getStartTime()).isEqualTo(LocalTime.of(6,0));
        assertThat(shift.getEndTime()).isEqualTo(LocalTime.of(14,0));
    }
    @Test
    void shouldThrowExceptionWhenDriverIsNull(){
        assertThatThrownBy(()->new Shift(1L,null,zone,LocalDate.of(2024,3,11),
                LocalTime.of(6,0),LocalTime.of(14,0)))
                .isInstanceOf(IllegalArgumentException.class).
        hasMessage("Shift driver must not be null");
    }
    @Test
    void shouldThrowExceptionWhenZoneIsNull(){
        assertThatThrownBy(()->new Shift(1L,driver,null,LocalDate.of(2024,3,11),
                LocalTime.of(6,0),LocalTime.of(14,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shift zone must not be null");
    }
    @Test
    void shouldThrowExceptionWhenDeploymentDayIsNull(){
        assertThatThrownBy(()->new Shift(1L,driver,zone,null,
                LocalTime.of(6,0),LocalTime.of(14,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shift deployment day must not be null");
    }
    @Test
    void shouldThrowExceptionWhenStartTimeIsNull(){
        assertThatThrownBy(()-> new Shift(1L,driver,zone,LocalDate.of(2024,3,11),
null,LocalTime.of(14,0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shift start time must not be null");
    }
    @Test
    void shouldThrowExceptionWhenEndTimeIsNull(){
        assertThatThrownBy(()-> new Shift(1L,driver,zone,LocalDate.of(2024,3,11),
                LocalTime.of(6,0),null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shift end time must not be null");
    }
}
