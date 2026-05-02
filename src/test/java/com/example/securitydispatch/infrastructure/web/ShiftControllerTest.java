package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.SecurityObjectService;
import com.example.securitydispatch.application.ShiftService;
import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.DriverEntity;
import com.example.securitydispatch.infrastructure.persistence.ZoneEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShiftController.class)
public class ShiftControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShiftService shiftService;

    private final Driver driver = new Driver(1L, "Max", "Mustermann");
    private final Zone zone = new Zone(1L, "Zone 1");
    private final LocalDate deploymentDate = LocalDate.of(2024, 3, 11);
    private final LocalTime startTime = LocalTime.of(6, 0);
    private final LocalTime endTime = LocalTime.of(14, 0);

    private final Shift shift = new Shift(1L, driver, zone, deploymentDate, startTime, endTime);
 private final Shift shiftB = new Shift(2L, driver, zone, deploymentDate, startTime, endTime);

    @Test
    void shouldCreateShift() throws Exception {
        when(shiftService.create( anyLong(), anyLong(), any(), any(), any()))
                .thenReturn(shift);

        mockMvc.perform(post("/api/shifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "id": 1,
                                "driverId": 1,
                                "zoneId": 1,
                                "deploymentDate": "2024-03-11",
                                "startTime": "06:00:00",
                                "endTime": "14:00:00"         }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deploymentDate").value("2024-03-11"));


    }
    @Test
    void shouldGetShiftById() throws Exception {
        when(shiftService.findById(1L))
                .thenReturn(shift);
        mockMvc.perform(get("/api/shifts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.deploymentDate").value("2024-03-11"));
    }
    @Test
    void shouldGetAllShifts() throws Exception {
        when(shiftService.findAll())
                .thenReturn(List.of(
                        shift,
                        shiftB));

        mockMvc.perform(get("/api/shifts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void shouldDeleteSShift() throws Exception {
        mockMvc.perform(delete("/api/shifts/1"))
                .andExpect(status().isNoContent());
    }
}
