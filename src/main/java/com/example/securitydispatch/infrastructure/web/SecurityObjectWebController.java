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
import java.util.List;
import java.util.Map;

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
        List<Zone> zones = zoneService.findAll();
        model.addAttribute("zones", zones);
        return "security-object-form";
    }
    @PostMapping
    public String create(@RequestParam String name,
                         @RequestParam long zoneId,
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
                    .closingTime(closingTime != null && !closingTime.isEmpty() ? LocalTime.parse(closingTime) : null)
                    .openingTime(openingTime != null && !openingTime.isEmpty() ? LocalTime.parse(openingTime) : null)
                    .build();
            SecurityObject newObj = new SecurityObject(0L, name, zone, address, config);
            securityObjectService.create(newObj);   // RUFT DIE NEUE METHODE AUF
            return "redirect:/security-objects";

    }

    @PostMapping("/{id}")
    public String update(@PathVariable long id,
                         @RequestParam String name,
                         @RequestParam long zoneId,
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
                .closingTime(closingTime != null && !closingTime.isEmpty() ? LocalTime.parse(closingTime) : null)
                .openingTime(openingTime != null && !openingTime.isEmpty() ? LocalTime.parse(openingTime) : null)
                .build();
        SecurityObject updated = new SecurityObject(id, name, zone, address, config);
        SecurityObjectEntity entity = SecurityObjectMapper.toEntity(updated);
        securityObjectService.update(id,name,zone.getId(),entity.getAddress(),entity.getStandardConfiguration());
        return "redirect:/security-objects";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        SecurityObject obj = securityObjectService.findById(id);
        model.addAttribute("object", obj);
        model.addAttribute("zones", zoneService.findAll());
        return "security-object-form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        securityObjectService.delete(id);
        return "redirect:/security-objects";
    }
}
