package com.example.RevierCheckliste.domain;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
public class DriveTest {
    @Test
    void shouldCreateADriverWithIdAndName(){
        Driver driver = new Driver(1L,"Max Mustermann");
        assertThat(driver.getId()).isEqualTo(1L);
        assertThat(driver.getName()).isEqualTo("Max Mustermann");
    }
    @Test
    void shouldThrowExceptionWhenNameIsNull(){
        assertThatThrownBy(()->new Driver(1L,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");
    }
    @Test
    void shouldThrowExceptionWhenNameIsBlank(){
        assertThatThrownBy(()->new Driver(1L,"     "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");
    }


}
