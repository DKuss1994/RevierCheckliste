package com.example.securitydispatch.infrastructure.persistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.DayOfWeek;
import java.util.List;
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
        SecurityObjectEntity object = securityObjectRepository.save(
                new SecurityObjectEntity("Object 1",zone,address,config));

        Optional<SecurityObjectEntity> found =
                securityObjectRepository.findById(object.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(object.getName());
        assertThat(found.get().getZone().getName()).isEqualTo(zone.getName());
    }
    @Test
    void shouldFindSecurityObjectByNameContaining() {
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
        securityObjectRepository.save(new SecurityObjectEntity
                ("Object A",zone,address,config));
        securityObjectRepository.save(new SecurityObjectEntity
                ("Object B",zone,address,config));
        securityObjectRepository.save(new SecurityObjectEntity
                ("Sonderobject",zone,address,config));

        List<SecurityObjectEntity> result = securityObjectRepository
                .findByNameContainingIgnoreCase("object");

        assertThat(result).hasSize(3);
    }

    @Test
    void shouldFindZoneByNameCaseInsensitive() {
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
        securityObjectRepository.save(new SecurityObjectEntity("Object A",zone,address,config));

        List<SecurityObjectEntity> result = securityObjectRepository.findByNameContainingIgnoreCase("OBJECT");


        assertThat(result).hasSize(1);
    }
}
