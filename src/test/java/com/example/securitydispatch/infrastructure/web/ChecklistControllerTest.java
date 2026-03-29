package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ChecklistGenerationService;
import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChecklistController.class)
public class ChecklistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChecklistGenerationService checklistGenerationService;

    @MockitoBean
    private ShiftRepository shiftRepository;

    @MockitoBean
    private SecurityObjectRepository securityObjectRepository;

    @MockitoBean
    private ChecklistRepository checklistRepository;

    @Test
    void shouldGenerateChecklistAndReturnResponse() throws Exception {

        Zone zone = new Zone(1L, "Zone 1");
        Driver driver = new Driver(1L, "Max", "Mustermann");
        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2024, 3, 11),
                LocalTime.of(6, 0), LocalTime.of(14, 0));

        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .build();

        // Shift Repository mocken
        ShiftEntity shiftEntity = new ShiftEntity(1L,
                new DriverEntity(1L, "Max", "Mustermann"),
                new ZoneEntity(1L, "Zone 1"),
                LocalDate.of(2024, 3, 11),
                LocalTime.of(6, 0), LocalTime.of(14, 0));

        when(shiftRepository.findById(1L)).thenReturn(Optional.of(shiftEntity));

// SecurityObject Repository mocken
        AddressEmbeddable address = new AddressEmbeddable("Musterstraße 1", "Berlin", "10115");
        StandardConfigurationEmbeddable configurationEmbeddable = new StandardConfigurationEmbeddable(
                2, null, null, null, null, null, null, null, null);
        SecurityObjectEntity securityObjectEntity = new SecurityObjectEntity(
                1L, "Object A", new ZoneEntity(1L, "Zone 1"), address, configurationEmbeddable);

        when(securityObjectRepository.findById(1L)).thenReturn(Optional.of(securityObjectEntity));

        Checklist checklist = new Checklist(1L, shift, config,
                LocalDateTime.now(), List.of());
        when(checklistGenerationService.generate(any(), any()))
                .thenReturn(checklist);
        mockMvc.perform(post("/checklists/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shiftId\": 1, \"securityObjectId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.inspectionCount").value(2));
    }
}