package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Driver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(DriverWebController.class)
class DriverWebControllerTest {

    @Autowired MockMvc mockMvc;
    @MockitoBean
    DriverService driverService;
    @MockitoBean ZoneService zoneService;

    @Test
    void shouldListDrivers () throws Exception{
        when(driverService.findAll())
                .thenReturn(List.of(new Driver(1L,"Max","Mustermann")));

        mockMvc.perform(MockMvcRequestBuilders.get("/drivers"))
                .andExpect(status().isOk())
                .andExpect(view().name("drivers"))
                .andExpect(model().attributeExists("drivers"));
    }
    @Test
    void shouldShowCreateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/drivers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("driver-form"));
    }

    @Test
    void shouldShowEditForm() throws Exception {
        Driver driver = new Driver(1L, "Max", "Mustermann");
        when(driverService.findById(1L)).thenReturn(driver);
        mockMvc.perform(MockMvcRequestBuilders.get("/drivers/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("driver-form"))
                .andExpect(model().attributeExists("driver"));
    }
}