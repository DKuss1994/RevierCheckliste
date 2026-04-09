package com.example.securitydispatch.infrastructure.persistence;
import com.example.securitydispatch.domain.Zone;
public class ZoneMapper {
    public static ZoneEntity toEntity(Zone zone){
        return new ZoneEntity(zone.getName());
    }
    public static Zone toDomain(ZoneEntity zoneEntity){
        return new Zone(zoneEntity.getId(),zoneEntity.getName());
    }
}
