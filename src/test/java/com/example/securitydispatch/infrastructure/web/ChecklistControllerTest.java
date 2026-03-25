package com.example.securitydispatch.infrastructure.web;
import com.example.securitydispatch.application.ChecklistGenerationService;
import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChecklistController.class)
public class ChecklistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChecklistGenerationService checklistGenerationService;

    @MockBean
    private ShiftRepository shiftRepository;

    @MockBean
    private SecurityObjectRepository securityObjectRepository;

    @MockBean
    private ChecklistRepository checklistRepository;

    @Test
    void shouldGenerateChecklistAndReturnResponse() throws Exception{
        Zone zone = new Zone(1L, "Zone 1");
        Driver driver = new Driver(1L, "Max", "Mustermann");
        Shift shift = new Shift(1L, driver, zone,
                LocalDate.of(2024, 3, 11),
                LocalTime.of(6, 0), LocalTime.of(14, 0));

        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(2)
                .build();

        Checklist checklist = new Checklist(1L, shift, config,
                LocalDateTime.now(), List.of());
        when(checklistGenerationService.generate(any(),any()))
                .thenReturn(checklist);
        mockMvc.perform(post("/checklists/generate")
                .content("{\"shiftId\": 1, \"securityObjectId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.inspectionCount").value(2));

    }
}
