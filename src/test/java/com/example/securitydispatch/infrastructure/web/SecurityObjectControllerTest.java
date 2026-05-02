package com.example.securitydispatch.infrastructure.web;
import com.example.securitydispatch.application.SecurityObjectService;
import com.example.securitydispatch.domain.Address;
import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.StandardConfiguration;
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


@WebMvcTest(SecurityObjectController.class)
class SecurityObjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SecurityObjectService securityObjectService;

    private final Zone zone = new Zone(1L, "Zone 1");
    private final Address address = new Address("Musterstraße 1", "Musterstadt", "123456");
    private final StandardConfiguration standardConfiguration = new StandardConfiguration.Builder().inspectionCount(2).build();
    private final SecurityObject securityObject = new SecurityObject
            (1L, "Object A", zone, address, standardConfiguration);
    private final SecurityObject securityObjectB = new SecurityObject
            (2L, "Object B", zone, address, standardConfiguration);

    @Test
    void shouldCreateSecurityObject() throws Exception {
        when(securityObjectService.create( any(), anyLong(), any(), any()))
                .thenReturn(securityObject);

        mockMvc.perform(post("/api/security-objects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": 1,
                                                                      "name": "Object A",
                                                                      "zoneId": 1,
                                                                      "street": "Musterstraße 1",
                                                                      "city": "Musterstadt",
                                                                      "postalCode": "123456",
                                                                      "inspectionCount": 2
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Object A"));

    }

    @Test
    void shouldGetSecurityObjectById() throws Exception {
        when(securityObjectService.findById(1L))
                .thenReturn(securityObject);
        mockMvc.perform(get("/api/security-objects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Object A"));


    }

    @Test
    void shouldGetAllSecurityObjects() throws Exception {
        when(securityObjectService.findAll())
                .thenReturn(List.of(
                        securityObject,
                        securityObjectB));

        mockMvc.perform(get("/api/security-objects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldUpdateSecurityObject() throws Exception {
        when(securityObjectService.update(anyLong(), any(), anyLong(), any(), any()))
                .thenReturn(new SecurityObject(1L, "Object A Updated", zone, address, standardConfiguration));

        mockMvc.perform(put("/api/security-objects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Object A Updated",
                                    "zoneId": 1,
                                    "street": "Musterstraße 1",
                                    "city": "Musterstadt",
                                    "postalCode": "123456",
                                    "inspectionCount": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Object A Updated"));
    }

    @Test
    void shouldDeleteSecurityObject() throws Exception {
        mockMvc.perform(delete("/api/security-objects/1"))
                .andExpect(status().isNoContent());
    }
}