package com.example.securitydispatch.domain;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ChecklistTest {

    private Shift buildShift(){
Zone zone = new Zone(1L,"Zone 1");

Driver driver = new Driver(1L,"Max","Mustermann");
return new Shift(1L,driver,zone, LocalDate.of(2026,3,15),
        LocalTime.of(19,0),LocalTime.of(6,0));
    }
    Shift shift = buildShift();
    private final Address address = new Address("Musterstraße 1","Bielefeld","33649");
    private final StandardConfiguration config = new StandardConfiguration.Builder()
            .inspectionCount(2)
            .build();
    private final SecurityObject securityObject = new SecurityObject(
            1L, "Object A", shift.getZone(), address, config);
    private final ChecklistEntry entry = new ChecklistEntry(securityObject, config);
    private final Checklist checklist = new Checklist(1L, shift, config,
            LocalDateTime.now(), List.of(), List.of(entry));
    List<Warning> warningList = List.of(new Warning("Warning"));
    @Test
    void shouldCreateAChecklistAsSnap(){

        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .build();
        Checklist checklist = new Checklist(1L,shift,config,LocalDateTime.now(),warningList,List.of(entry));
        assertThat(checklist.getId()).isEqualTo(1L);
        assertThat(checklist.getShift()).isEqualTo(shift);
        assertThat(checklist.getConfiguration()).isEqualTo(config);
        assertThat(checklist.getGeneratedAt()).isNotNull();
    }
    @Test
    void shouldThrowExceptionWhenShiftIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, null,
                new StandardConfiguration.Builder().build(), LocalDateTime.now(),warningList,List.of(entry)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist shift must not be null");
    }
    @Test
    void shouldThrowExceptionWhenConfigIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, shift,
                null, LocalDateTime.now(),warningList,List.of(entry)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist configuration must not be null");
    }
    @Test
    void shouldThrowExceptionWhenGeneratedAtIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, shift,
                new StandardConfiguration.Builder().build(), null,warningList,List.of(entry)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist generatedAt must not be null");
    }
    @Test
    void shouldThrowExceptionWhenWarningsIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, shift,
                new StandardConfiguration.Builder().build(), LocalDateTime.now(),null,List.of(entry)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist warnings must not be null");
    }
    @Test
    void shouldThrowExceptionWhenEntriesIsNull() {
        assertThatThrownBy(() -> new Checklist(1L, shift,
                new StandardConfiguration.Builder().build(), LocalDateTime.now(),warningList,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Checklist entries must not be null");
    }
    @Test
    void shouldCreateChecklistWithEntries(){
        assertThat(checklist.getEntries()).hasSize(1);
        assertThat(checklist.getEntries().getFirst().getSecurityObject().getName())
                .isEqualTo("Object A");

    }
}
