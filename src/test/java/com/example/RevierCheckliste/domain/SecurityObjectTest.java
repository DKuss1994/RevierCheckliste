package com.example.RevierCheckliste.domain;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
public class SecurityObjectTest {
    private final Zone zone = new Zone(1L,"Zone1");
private final Address address = new Address("Musterstraße 1","Berilin","10115");
    @Test
    void shouldCreateSecurityObjectWithIdNameAndZone(){
SecurityObject securityObject = new SecurityObject(1L,"Object",zone,address);
assertThat(securityObject.getAddress()).isEqualTo(address);
assertThat(securityObject.getZone()).isEqualTo(zone);
assertThat(securityObject.getName()).isEqualTo("Object");
assertThat(securityObject.getId()).isEqualTo(1L);
    }
    @Test
    void shouldThrowExceptionWhenObjectIsBlankOrNull(){
assertThatThrownBy(()->new SecurityObject(1L,"      ",zone,address))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Object name must not be blank");
assertThatThrownBy(()->new SecurityObject(1L,null,zone,address))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Object name must not be blank");
    }
    @Test
    void shouldThrowExceptionWhenZoneIsNull(){
        assertThatThrownBy(()->new SecurityObject(1L,"Object",null,address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone name must not be null");
    }
    @Test
    void shouldThrowExceptionWhenAddressIsNull(){
        assertThatThrownBy(()->new SecurityObject(1L,"Object",zone,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone name must not be null");
    }
    @Test
    void shouldThrowExceptionWhenAddressAndZoneIsNull(){
        assertThatThrownBy(()->new SecurityObject(1L,"Object",null,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone name must not be null");
    }
}
