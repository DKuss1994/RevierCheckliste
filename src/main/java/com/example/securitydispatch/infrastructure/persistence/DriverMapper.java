package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Zone;

public class DriverMapper {
    public static DriverEntity toEntity(Driver driver) {
        DriverEntity entity = new DriverEntity(
                driver.getFirstName(),
                driver.getLastName());
        for (Zone zone : driver.getAssignedZones()) {
            entity.addAssignedZone(ZoneMapper.toEntity(zone));
        }
        return entity;
    }

    public static Driver toDomain(DriverEntity entity) {
        Driver driver= new Driver(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName());
        for (ZoneEntity zone : entity.getAssignedZones()) {
            driver.addAssignedZone(ZoneMapper.toDomain(zone));
        }
        return driver;
    }


}
