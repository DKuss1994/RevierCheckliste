package com.example.securitydispatch.domain;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChecklistEntryTest {
    private final Zone zone = new Zone(1L, "Zone 1");
    private final Address address = new Address("Musterstraße 1", "Berlin", "10115");
    private final StandardConfiguration config = new StandardConfiguration.Builder()
            .inspectionCount(2)
            .inspectionDays(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY))
            .build();
    private final SecurityObject securityObject = new SecurityObject(
            1L, "Object A", zone, address, config);

    @Test

    void shouldCreateChecklistEntry(){
        ChecklistEntry entry = new ChecklistEntry(securityObject,config);

        assertThat(entry.getSecurityObject()).isEqualTo(securityObject);
        assertThat(entry.getResolvedConfiguration()).isEqualTo(config);
    }
    @Test
    void shouldThrowExceptionWhenSecurityObjectIsNull(){

        assertThatThrownBy(()-> new ChecklistEntry(null,config))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ChecklistEntry security object must not be null");


    }
    @Test
    void shouldThrowExceptionWhenStandardConfigurationIsNull(){

        assertThatThrownBy(()-> new ChecklistEntry(securityObject,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ChecklistEntry standard configuration must not be null");


    }
}
