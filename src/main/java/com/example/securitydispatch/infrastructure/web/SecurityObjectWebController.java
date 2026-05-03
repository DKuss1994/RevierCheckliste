package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.SecurityObjectService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Address;
import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.StandardConfiguration;
import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.AddressEmbeddable;
import com.example.securitydispatch.infrastructure.persistence.SecurityObjectEntity;
import com.example.securitydispatch.infrastructure.persistence.SecurityObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@Controller
@RequestMapping("/security-objects")
public class SecurityObjectWebController {
    private final ZoneService zoneService;
    private final SecurityObjectService securityObjectService;
    private final DriverService driverService;

    public SecurityObjectWebController(ZoneService zoneService, SecurityObjectService securityObjectService, DriverService driverService) {
        this.zoneService = zoneService;
        this.securityObjectService = securityObjectService;
        this.driverService = driverService;
    }
    @GetMapping
    public String listSecurityObjects(Model model){
        model.addAttribute("objects",securityObjectService.findAll());
        return "security-objects";
    }
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("zones", zoneService.findAll());
        return "security-object-form";
    }
    @PostMapping
    public String create(@RequestParam String name,
                         @RequestParam Long zoneId,
                         @RequestParam String street,
                         @RequestParam String city,
                         @RequestParam String zip,
                         @RequestParam(required = false) Integer inspectionCount,
                         @RequestParam(required = false) String closingTime,
                         @RequestParam(required = false) String openingTime) {

        Zone zone = zoneService.findById(zoneId);
        Address address = new Address(street, city, zip);
        StandardConfiguration config = new StandardConfiguration.Builder()
                .inspectionCount(inspectionCount)
                .closingTime(closingTime != null ? LocalTime.parse(closingTime) : null)
                .openingTime(openingTime != null ? LocalTime.parse(openingTime) : null)
                .build();
        SecurityObject newObj = new SecurityObject(0L, name, zone, address, config);

        SecurityObjectEntity securityObjectEntity = SecurityObjectMapper.toEntity(newObj);
        securityObjectService.create(securityObjectEntity.getName(),securityObjectEntity.getZone().getId(),
                securityObjectEntity.getAddress(),securityObjectEntity.getStandardConfiguration());
        return "redirect:/security-objects";
    }
}
