package com.example.securitydispatch.infrastructure.web;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Zone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ZoneWebController.class)
public class ZoneWebControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean
    ZoneService zoneService;

    @Test
    void shouldListZones () throws Exception{
        when(zoneService.findAll())
                .thenReturn(List.of(new Zone(1L,"Zone 1")));

        mockMvc.perform(get("/zones"))
                .andExpect(status().isOk())
                .andExpect(view().name("zones"))
                .andExpect(model().attributeExists("zones"));
    }
    @Test
    void shouldShowCreateForm() throws Exception {
        mockMvc.perform(get("/zones/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("zone-form"));
    }
}
