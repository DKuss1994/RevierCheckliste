package com.example.securitydispatch.infrastructure.web;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Zone;
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


@WebMvcTest(ZoneController.class)
class ZoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ZoneService zoneService;

    @Test
    void shouldCreateZone()throws Exception{
        when(zoneService.create(any()))
                .thenReturn(new Zone(1L,"Zone 1"));

        mockMvc.perform(post("/api/zones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"Zone 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Zone 1"));

    }
    @Test
    void shouldGetZoneById()throws Exception{
        when(zoneService.findById(1L))
                .thenReturn(new Zone(1L,"Zone 1"));
        mockMvc.perform(get("/api/zones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Zone 1"));


    }
    @Test
    void shouldGetAllZones()throws Exception{
        when(zoneService.findAll())
                .thenReturn(List.of(
                        new Zone(1L,"Zone 1"),
                        new Zone(2L,"Zone 2")));

        mockMvc.perform(get("/api/zones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void shouldUpdateZone()throws Exception{
        when(zoneService.update(anyLong(),any()))
                .thenReturn(new Zone(1L,"Zone 1 Updated"));

        mockMvc.perform(put("/api/zones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Zone 1 Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Zone 1 Updated"));
    }
    @Test
    void shouldDeleteZone() throws Exception{
        mockMvc.perform(delete("/api/zones/1"))
                .andExpect(status().isNoContent());
    }
}
