package com.example.securitydispatch.infrastructure.persistence;
import com.example.securitydispatch.domain.*;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
public class SecurityObjectMapperTest {

    private final Zone zone = new Zone(1L, "Zone 1");
    private final Address address = new Address("Musterstraße 1", "Berlin", "10115");
    private final StandardConfiguration config = new StandardConfiguration.Builder()
            .inspectionCount(2)
            .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
            .build();
    private final SecurityObject securityObject = new SecurityObject(
            1L,
            "Object A",
            zone,
            address,
            config
    );
    @Test
    void shouldMapSecurityObjectToDomainAndBack(){
        SecurityObjectEntity entity = SecurityObjectMapper.toEntity(securityObject);
        SecurityObject mapped = SecurityObjectMapper.toDomain(entity);
        assertThat(mapped).isNotNull();
        assertThat(mapped.getName()).isEqualTo(securityObject.getName());
        assertThat(mapped.getAddress().getStreet()).isEqualTo(address.getStreet());
        assertThat(mapped.getZone().getName()).isEqualTo(zone.getName());
        assertThat(mapped.getStandardConfiguration().getInspectionCount()).isEqualTo(securityObject.getStandardConfiguration().getInspectionCount());

    }
}
