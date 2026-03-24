package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Zone;

public class DriverMapper {
    public static DriverEntity toEntity(Driver driver) {
        return new DriverEntity(driver.getId(), driver.getFirstName(), driver.getLastName());
    }

    public static Driver toDomain(DriverEntity driver) {
        return new Driver(driver.getId(), driver.getFirstName(), driver.getLastName());
    }
}
