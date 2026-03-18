package com.example.securitydispatch.domain;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ResolutionResultTest {
    @Test
    void shouldCreateResolutionResultWithConfigurationAndNoWarnings(){
        int count = 2;
        StandardConfiguration configuration = new StandardConfiguration.Builder()
                .inspectionCount(count)
                .build();
        ResolutionResult result = new ResolutionResult(configuration,List.of());
        assertThat(result.getConfiguration().getInspectionCount()).hasValue(2);
        assertThat(result.getWarnings()).isEmpty();
    }
    @Test
    void shouldCreateResolutionResultWithWarnings(){
        int count = 2;
        StandardConfiguration configuration = new StandardConfiguration.Builder()
                .build();
        Warning warning = new Warning("Inspection count cannot be negative, set to 0");
        ResolutionResult result = new ResolutionResult(configuration,List.of(warning));
        assertThat(result.getWarnings()).containsExactly(warning);

    }
     @Test
    void shouldThrowExceptionConfigurationIsNull(){
assertThatThrownBy(()-> new ResolutionResult(null,List.of()))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Configuration must not be null");

     }
     @Test
    void shouldThrowExceptionWarningIsNull(){
         int count = 2;
         StandardConfiguration configuration = new StandardConfiguration.Builder()
                 .inspectionCount(count)
                 .build();
assertThatThrownBy(()-> new ResolutionResult(configuration,null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Warning must not be null");

     }


}
