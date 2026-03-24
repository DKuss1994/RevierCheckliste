package com.example.securitydispatch.infrastructure.persistence;
import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Zone;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class DriverMapperTest {
    @Test
    void shouldMapDriverToDomainAndBack(){
        Driver driver = new Driver(1L,"Max","Hans");
        DriverEntity entity = DriverMapper.toEntity(driver);
        Driver toDomain = DriverMapper.toDomain(entity);
        assertThat(toDomain.getId()).isEqualTo(driver.getId());
        assertThat(toDomain.getFirstName()).isEqualTo(driver.getFirstName());
    }
    @Test
    void shouldMapAssignedZones(){
        Driver driver = new Driver(1L, "Max", "Mustermann");
        Zone zone = new Zone(1L, "Zone 1");
        driver.assignedZones(zone);
        DriverEntity entity = DriverMapper.toEntity(driver);
        Driver mapped = DriverMapper.toDomain(entity);
        assertThat(mapped.getAssignedZones()).isEqualTo(1);
        assertThat(mapped.getAssignedZones().get(0).getName()).isEqualTo("Zone 1");
    }
}
