package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.ChecklistApplicationService;
import com.example.securitydispatch.domain.*;
import com.example.securitydispatch.infrastructure.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChecklistController.class)
public class ChecklistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChecklistApplicationService checklistApplicationService;

    @Test
    void shouldGenerateChecklistAndReturnResponse() throws Exception {

        ChecklistResponse response = new ChecklistResponse(
                1L,
                LocalDateTime.now(),
                2,
                List.of());

        when(checklistApplicationService.generate(any()))
                .thenReturn(response);

        mockMvc.perform(post("/checklists/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"shiftId\": 1, \"securityObjectId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.inspectionCount").value(2));
    }
}