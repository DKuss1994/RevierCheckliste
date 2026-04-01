package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.domain.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DriverController.class)
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DriverService driverService;

    private final Driver driver = new Driver(1L,"Max","Mustermann");

    @Test
    void shouldCreateDriver()throws Exception{
        when(driverService.create(anyLong(),any(),any()))
                .thenReturn(driver);

        mockMvc.perform(post("/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"firstName\": \"Max, \"lastName\": \"Mustermann\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Max"))
                .andExpect(jsonPath("$.lastName").value("Mustermann"));

    }
    @Test
    void shouldGetDriverById()throws Exception{
        when(driverService.findById(1L))
                .thenReturn(driver);
        mockMvc.perform(get("/drivers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Max"))
                .andExpect(jsonPath("$.lastName").value("Mustermann"));


    }
    @Test
    void shouldGetAllDrivers()throws Exception{
        when(driverService.findAll())
                .thenReturn(List.of(
                        driver,
                        new Driver(2L,"David","Langen")));

        mockMvc.perform(get("/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void shouldUpdateDriver()throws Exception{
        when(driverService.update(anyLong(),any(),any()))
                .thenReturn(new Driver(1L,"Max Update","Update"));

        mockMvc.perform(put("/drivers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Max Update, \"lastName\": \"Update\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Max Update"))
                .andExpect(jsonPath("$.lastName").value("Update"));
    }
    @Test
    void shouldDeleteZone() throws Exception{
        mockMvc.perform(delete("/drivers/1"))
                .andExpect(status().isNoContent());
    }
}
