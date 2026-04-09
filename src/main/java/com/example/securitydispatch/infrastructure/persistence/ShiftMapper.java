package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.domain.Zone;

public class ShiftMapper {
    public static ShiftEntity toEntity(Shift shift) {
        DriverEntity driver = DriverMapper.toEntity(shift.getDriver());
        ZoneEntity zone = ZoneMapper.toEntity(shift.getZone());
        return new ShiftEntity
                ( driver, zone, shift.getDeploymentDate(),
                        shift.getStartTime(), shift.getEndTime());
    }

    public static Shift toDomain(ShiftEntity entity) {
        Driver driver = DriverMapper.toDomain(entity.getDriver());
        Zone zone = ZoneMapper.toDomain(entity.getZone());
        return new Shift(entity.getId(),driver,zone,
                entity.getDeploymentDate(),entity.getStartTime(),
                entity.getEndTime());
    }
}
