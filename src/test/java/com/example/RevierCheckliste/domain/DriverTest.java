package com.example.RevierCheckliste.domain;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
public class DriverTest {
    @Test
    void shouldCreateADriverWithIdAndName(){
        Driver driver = new Driver(1L,"Max","Mustermann");
        assertThat(driver.getId()).isEqualTo(1L);
        assertThat(driver.getFirstName()).isEqualTo("Max");

        assertThat(driver.getLastName()).isEqualTo("Mustermann");
    }
    @Test
    void shouldThrowExceptionWhenFirstNameIsNull() {
        assertThatThrownBy(() -> new Driver(1L, null, "Mustermann"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");}
    @Test
        void shouldThrowExceptionWhenLastNameIsNull(){
        assertThatThrownBy(()-> new Driver(1L,"Max",null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");}

        @Test
        void shouldThrowExceptionWhenFirstNameAndLastNameIsNull(){
        assertThatThrownBy(()-> new Driver(1L,null,null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");

    }
    @Test
    void shouldThrowExceptionWhenFirstNameIsBlank(){
        assertThatThrownBy(()->new Driver(1L,"     ","Mustermann"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");}

    @Test
    void shouldThrowExceptionWhenLastNameIsBlank(){
        assertThatThrownBy(()->new Driver(1L,"Max","      "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");}

    @Test
    void shouldThrowExceptionWhenFirstNameAndLastNameIsBlank(){
        assertThatThrownBy(()->new Driver(1L,"     ","               "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");
    }
   @Test
    void shouldThrowExceptionWhenFirstNameAndLastNameIsBlankOrNull(){
        assertThatThrownBy(()->new Driver(1L,null,"    "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");
        assertThatThrownBy(()->new Driver(1L,"       ",null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be blank");
   }
   @Test
    void shouldHaveAssignedZoneByDefault(){
        Driver driver = new Driver(1L,"Max","Mustermann");
        assertThat(driver.getAssignedZones()).isEmpty();
   }
   @Test
    void shouldAssignedZoneToDriver(){
       Driver driver = new Driver(1L,"Max","Mustermann");
Zone zone = new Zone(1L,"Zone 1");
driver.assignedZones(zone);
       assertThat(driver.getAssignedZones()).containsExactly(zone);

   }


}
