package com.example.securitydispatch.infrastructure.web;

import com.example.securitydispatch.application.DriverService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Driver;
import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.Zone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    @PostMapping
    public ResponseEntity<Driver> create(@RequestBody DriverRequest request){
        Driver driver = driverService.create(request.getFirstName(), request.getLastName());
        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }
    @PostMapping("/{driverId}/zones/{zoneId}")
    public Driver assignZone(
            @PathVariable Long driverId,
            @PathVariable Long zoneId
    ) {
        return driverService.assignZone(driverId, zoneId);
    }
    @GetMapping("{id}")
    public ResponseEntity<Driver> findById(@PathVariable long id){
        return ResponseEntity.ok(driverService.findById(id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<Driver>> search(@RequestParam String name) {
        return ResponseEntity.ok(driverService.search(name,name));
    }
    @GetMapping
    public ResponseEntity<List<Driver>>findAll(){
        return ResponseEntity.ok(driverService.findAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Driver> update(@PathVariable long id,
                                       @RequestBody DriverRequest request){
        return ResponseEntity.ok(driverService.update(id,request.getFirstName(),request.getLastName()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        driverService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
