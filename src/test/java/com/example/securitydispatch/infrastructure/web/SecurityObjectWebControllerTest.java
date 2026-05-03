package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.SecurityObjectService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Address;
import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.StandardConfiguration;
import com.example.securitydispatch.domain.Zone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SecurityObjectWebController.class)
public class SecurityObjectWebControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    SecurityObjectService securityObjectService;

    @MockitoBean
    ZoneService zoneService;

    @MockitoBean
    DriverService driverService;

    Zone zone = new Zone(1L, "Zone 1");

    Address address = new Address(
            "Street",
            "City",
            "12345"
    );

    StandardConfiguration standardConfiguration = new StandardConfiguration.Builder().
            inspectionCount(2).build();

    SecurityObject securityObject = new SecurityObject(
            1L,
            "Object A",
            zone,
            address,
            standardConfiguration
            );

    @Test
    void shouldListSecurityObjects() throws Exception {
        when(securityObjectService.findAll())
                .thenReturn(List.of(securityObject));

        mockMvc.perform(get("/security-objects"))
                .andExpect(status().isOk())
                .andExpect(view().name("security-objects"))
                .andExpect(model().attributeExists("objects"));
    }
}
