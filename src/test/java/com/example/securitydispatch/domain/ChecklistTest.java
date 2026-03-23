package com.example.securitydispatch.domain;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    Shift shift = buildShift();
    List<Warning> warningList = List.of(new Warning("Warning"));
    @Test
    void shouldCreateAChecklistAsSnap(){

        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .build();
        Checklist checklist = new Checklist(1L,shift,config,LocalDateTime.now(),warningList);
        assertThat(checklist.getId()).isEqualTo(1L);
        assertThat(checklist.getShift()).isEqualTo(shift);
        assertThat(checklist.getConfig()).isEqualTo(config);
        assertThat(checklist.getGeneratedAt()).isNotNull();
    }
    @Test
    void shouldThrowExceptionWhenShiftIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, null,
                new StandardConfiguration.Builder().build(), LocalDateTime.now(),warningList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist shift must not be null");
    }
    @Test
    void shouldThrowExceptionWhenConfigIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, shift,
                null, LocalDateTime.now(),warningList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist configuration must not be null");
    }
    @Test
    void shouldThrowExceptionWhenGeneratedAtIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, shift,
                new StandardConfiguration.Builder().build(), null,warningList))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist generatedAt must not be null");
    }
    @Test
    void shouldThrowExceptionWhenWarningsIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, shift,
                new StandardConfiguration.Builder().build(), LocalDateTime.now(),null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist warnings must not be null");
    }
}
