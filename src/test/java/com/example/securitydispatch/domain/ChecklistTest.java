package com.example.securitydispatch.domain;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ChecklistTest {
    private Shift buildShift(){
Zone zone = new Zone(1L,"Zone 1");
Address address = new Address("Musterstraße 1","Bielefeld","33649");
Driver driver = new Driver(1L,"Max","Mustermann");
return new Shift(1L,driver,zone, LocalDate.of(2026,3,15),
        LocalTime.of(19,0),LocalTime.of(6,0));
    }
    @Test
    void shouldCreateAChecklistAsSnap(){
        Shift shift = buildShift();
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .build();
        Checklist checklist = new Checklist(1L,shift,config,LocalDateTime.now());
        assertThat(checklist.getId()).isEqualTo(1L);
        assertThat(checklist.getShift()).isEqualTo(shift);
        assertThat(checklist.getConfig()).isEqualTo(config);
    }
    @Test
    void shouldThrowExceptionWhenShiftIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, null,
                new StandardConfiguration.Builder().build(), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist shift must not be null");
    }
}
