package com.example.securitydispatch.infrastructure.persistence;

import com.example.securitydispatch.domain.Zone;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class ZoneMapperTest {
    @Test
    void shouldMapZoneToDomainAndBack(){
        Zone zone = new Zone(1L,"Zone 1");
        ZoneEntity entity = ZoneMapper.toEntity(zone);
        Zone toDomain = ZoneMapper.toDomain(entity);
        assertThat(toDomain.getId()).isEqualTo(zone.getId());
        assertThat(toDomain.getName()).isEqualTo(zone.getName());
    }
}
