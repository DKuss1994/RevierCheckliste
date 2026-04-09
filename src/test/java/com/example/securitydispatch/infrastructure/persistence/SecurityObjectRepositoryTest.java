package com.example.securitydispatch.infrastructure.persistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class SecurityObjectRepositoryTest {
    @Autowired
    private SecurityObjectRepository securityObjectRepository;
    @Autowired
    private ZoneRepository zoneRepository;

    @Test
    void shouldSaveAndLoadSecurityObject(){
        ZoneEntity zone = new ZoneEntity("Zone 1");
        zoneRepository.save(zone);
        AddressEmbeddable address = new AddressEmbeddable
                ("Musterstraße 1","Berlin","10115");
        StandardConfigurationEmbeddable config = new StandardConfigurationEmbeddable(
                2,
                Set.of(DayOfWeek.MONDAY,DayOfWeek.FRIDAY),
                null,
                null,
                null,
                null,
                null,
                null,
                "Test notes"
        );
        SecurityObjectEntity object = new SecurityObjectEntity(
                1L,"Object 1",zone,address,config);
        securityObjectRepository.save(object);
        Optional<SecurityObjectEntity> found =
                securityObjectRepository.findById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(object.getName());
        assertThat(found.get().getZone().getName()).isEqualTo(zone.getName());
    }
}
