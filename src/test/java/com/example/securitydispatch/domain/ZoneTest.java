package com.example.securitydispatch.domain;


import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZoneTest {
    @Test
    void shouldBeEqualWhenIdAndNameAreTheSame() {
        Zone zone1 = new Zone(1L, "Zone 1");
        Zone zone2 = new Zone(1L, "Zone 1");

        assertThat(zone1).isEqualTo(zone2);
    }
    @Test
    void shouldCreateZoneWithIdAndName(){
        Zone zone = new Zone(1L,"Zone 1");
        assertThat(zone.getId()).isEqualTo(1L);
        assertThat(zone.getName()).isEqualTo("Zone 1");
    }
    @Test
    void shouldThrowExceptionWhenNameIsNull(){
        assertThatThrownBy(()->new Zone(1L,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone must not be blank");
    }
    @Test
    void shouldThrowExceptionWhenNameIsBlank(){
        assertThatThrownBy(()->new Zone(1L,"      "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Zone must not be blank");
    }

}
