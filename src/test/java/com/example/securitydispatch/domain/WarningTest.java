package com.example.securitydispatch.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarningTest {
    @Test
    void shouldCreateWarningWithMessage(){
        Warning warning = new Warning("Inspection count cannot be negative, set to 0");
        assertThat(warning.getMessage())
                .isEqualTo("Inspection count cannot be negative, set to 0");

    }
    @Test
    void shouldThrowExceptionWhenMessageIsBlank(){
        assertThatThrownBy(()->new Warning("       "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Warning message must not blank");
    }
    @Test
    void shouldThrowExceptionWhenMessageIsNull(){
        assertThatThrownBy(()->new Warning(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Warning message must not blank");
    }
}
