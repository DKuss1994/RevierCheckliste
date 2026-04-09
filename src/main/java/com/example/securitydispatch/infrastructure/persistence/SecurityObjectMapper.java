package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.StandardConfiguration;
import com.example.securitydispatch.domain.*;

public class SecurityObjectMapper {
    public static SecurityObjectEntity toEntity(SecurityObject securityObject) {
        ZoneEntity zone = ZoneMapper.toEntity(securityObject.getZone());
        AddressEmbeddable address = new AddressEmbeddable(
                securityObject.getAddress().getStreet(),
                securityObject.getAddress().getCity(),
                securityObject.getAddress().getZIPCode()
        );
        StandardConfigurationEmbeddable configEmbeddable = getStandardConfigurationEmbeddable(securityObject);
        return new SecurityObjectEntity(
        securityObject.getName(),
        zone,
        address,
        configEmbeddable);

    }

    private static StandardConfigurationEmbeddable getStandardConfigurationEmbeddable(SecurityObject securityObject) {
        StandardConfiguration config = securityObject.getStandardConfiguration();
        return new StandardConfigurationEmbeddable(
                config.getInspectionCount().orElse(null),
                config.getInspectionDays().orElse(null),
                config.getOpeningTime().orElse(null),
                config.getOpeningDays().orElse(null),
                config.getClosingTime().orElse(null),
                config.getClosingDays().orElse(null),
                config.getInspectionWindowStart().orElse(null),
                config.getInspectionWindowEnd().orElse(null),
                config.getNotes().orElse(null));
    }

    public static SecurityObject toDomain(SecurityObjectEntity entity) {
        Zone zone = ZoneMapper.toDomain(entity.getZone());
        Address address = new Address(
                entity.getAddress().getStreet(),
                entity.getAddress().getCity(),
                entity.getAddress().getPostalCode()
        );
        StandardConfigurationEmbeddable config = entity.getStandardConfiguration();
        StandardConfiguration standardConfiguration = new StandardConfiguration.Builder()
                .inspectionCount(config.getInspectionCount())
                .inspectionDays(config.getInspectionDays())
                .openingTime(config.getOpeningTime())
                .openingDays(config.getOpeningDays())
                .closingTime(config.getClosingTime())
                .closingDays(config.getClosingDays())
                .inspectionWindowStart(config.getInspectionWindowStart())
                .inspectionWindowEnd(config.getInspectionWindowEnd())
                .notes(config.getNotes())
                .build();
        return new SecurityObject(entity.getId(),
                entity.getName(),
                zone,
                address,
                standardConfiguration);
    }
}
