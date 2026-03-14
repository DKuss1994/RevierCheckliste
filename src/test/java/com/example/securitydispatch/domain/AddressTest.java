package com.example.securitydispatch.domain;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AddressTest {
    @Test
    void shouldCreateAddressWithAllFields(){
        Address address = new Address("Musterstraße 1","Berlin","10115");
        assertThat(address.getStreet()).isEqualTo("Musterstraße 1");
        assertThat(address.getCity()).isEqualTo("Berlin");
        assertThat(address.getZIPCode()).isEqualTo("10115");
    }
    @Test
    void shouldThrowExceptionWhenStreetIsBlank(){
        assertThatThrownBy(()->new Address("            ","Berlin","10115"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Street must not be blank");
    }
    @Test
    void shouldThrowExceptionWhenCityIsBlank(){
        assertThatThrownBy(()-> new Address("Musterstraße 1","        ","10115"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("City must not be blank");
    }
    @Test
    void shouldThrowExceptionWhenZIPIsBlank(){
        assertThatThrownBy(()->new Address("Musterstraße 1","Berlin","    "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ZIP Code must not be blank");
    }

}
