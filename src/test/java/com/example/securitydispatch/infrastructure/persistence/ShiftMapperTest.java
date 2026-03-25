package com.example.securitydispatch.infrastructure.persistence;
import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.domain.Zone;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ShiftMapperTest {
    @Test
    void shouldMapShiftToDomainAndBack(){
        Driver driver = new Driver(1L,"Max","Muster");
        Zone zone = new Zone(1L,"Zone 1");
        LocalDate startDay = LocalDate.of(2026,3,3);
        LocalTime startTime = LocalTime.of(22,0);
        LocalTime endTime = LocalTime.of(6,0);

        Shift shift = new Shift(1L,driver,zone,startDay,startTime,endTime);
        ShiftEntity entity = ShiftMapper.toEntity(shift);
        Shift toDomain = ShiftMapper.toDomain(entity);
        assertThat(toDomain.getId()).isEqualTo(shift.getId());
        assertThat(toDomain.getDeploymentDate()).isEqualTo(startDay);
        assertThat(toDomain.getDriver().getFirstName()).isEqualTo("Max");
        assertThat(toDomain.getZone().getName()).isEqualTo("Zone 1");
        assertThat(toDomain.getStartTime()).isEqualTo(startTime);
        assertThat(toDomain.getEndTime()).isEqualTo(endTime);
    }
}
