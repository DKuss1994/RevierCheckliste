package com.example.RevierCheckliste.domain;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
public class SecurityObjectTest {
    private final Zone zone = new Zone(1L,"Zone1");
private final Address address = new Address("Musterstraße 1","Berilin","10115");
private final StandardConfiguration config = new StandardConfiguration.Builder()
            .inspectionCount(2)
                .inspectionDays(Set.of(DayOfWeek.MONDAY,DayOfWeek.FRIDAY))
            .build();
    @Test
    void shouldCreateSecurityObjectWithIdNameAndZone(){
SecurityObject securityObject = new SecurityObject(1L,"Object",zone,address, config);
assertThat(securityObject.getAddress()).isEqualTo(address);
assertThat(securityObject.getZone()).isEqualTo(zone);
assertThat(securityObject.getName()).isEqualTo("Object");
assertThat(securityObject.getId()).isEqualTo(1L);
    }
    @Test
    void shouldThrowExceptionWhenObjectIsBlankOrNull(){
assertThatThrownBy(()->new SecurityObject(1L,"      ",zone,address, config))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Object name must not be blank");
assertThatThrownBy(()->new SecurityObject(1L,null,zone,address, config))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Object name must not be blank");
    }
    @Test
    void shouldThrowExceptionWhenZoneIsNull(){
        assertThatThrownBy(()->new SecurityObject(1L,"Object",null,address, config))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone name must not be null");
    }
    @Test
    void shouldThrowExceptionWhenAddressIsNull(){
        assertThatThrownBy(()->new SecurityObject(1L,"Object",zone,null, config))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Address name must not be null");
    }
    @Test
    void shouldCreateSecurityObjectWithStandardConfiguration(){
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .inspectionDays(Set.of(DayOfWeek.MONDAY,DayOfWeek.FRIDAY))
                .build();
        SecurityObject object = new SecurityObject(1L,"Object",zone,address,config);
        assertThat(object.getStandardCofiguration()).isEqualTo(config);
    }
    @Test
    void shouldThrowExceptionWhenConfigIsNull(){
        assertThatThrownBy(()->new SecurityObject(1L,"Object",zone,address,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Configuration must not be null");
    }

}
