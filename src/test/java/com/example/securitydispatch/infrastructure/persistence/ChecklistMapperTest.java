package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.Checklist;
import com.example.securitydispatch.domain.*;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ChecklistMapperTest {
    @Test
    void shouldMapChecklistToDomainAndBack(){
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
                .build();
        Driver driver = new Driver(1L,"Max","Muster");
        Zone zone = new Zone(1L,"Zone 1");
        LocalDate startDay = LocalDate.of(2026,3,3);
        LocalTime startTime = LocalTime.of(22,0);
        LocalTime endTime = LocalTime.of(6,0);
        LocalDateTime generatedAt = LocalDateTime.now();
        Shift shift = new Shift(1L,driver,zone,startDay,startTime,endTime);
        Warning warning = new Warning("Stop");
        List<Warning> warnings = List.of(warning);
        Checklist checklist = new Checklist(1L,shift,config,generatedAt,warnings, List.of());
        ChecklistEntity entity = ChecklistMapper.toEntity(checklist);
        Checklist toDomain = ChecklistMapper.toDomain(entity);
        assertThat(toDomain.getId()).isEqualTo(checklist.getId());
        assertThat(toDomain.getConfiguration().getInspectionCount()).isEqualTo(config.getInspectionCount());
        assertThat(toDomain.getShift().getDriver().getFirstName()).isEqualTo(driver.getFirstName());
        assertThat(toDomain.getShift().getZone().getName()).isEqualTo(zone.getName());
        assertThat(toDomain.getWarnings()).hasSize(1);
        assertThat(toDomain.getWarnings().get(0).getMessage()).isEqualTo("Stop");

    }


}
