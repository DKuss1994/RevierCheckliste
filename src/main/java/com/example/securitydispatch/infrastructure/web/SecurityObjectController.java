package com.example.securitydispatch.infrastructure.web;
import com.example.securitydispatch.application.SecurityObjectService;

import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.AddressEmbeddable;
import com.example.securitydispatch.infrastructure.persistence.StandardConfigurationEmbeddable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/security-objects")
public class SecurityObjectController {
    private final SecurityObjectService securityObjectService;

    public SecurityObjectController(SecurityObjectService securityObjectService) {
        this.securityObjectService = securityObjectService;
    }
    @PostMapping
    public ResponseEntity<SecurityObject> create(@RequestBody SecurityObjectRequest request){
        AddressEmbeddable addressEmbeddable = getAddressEmbeddable(request);
        StandardConfigurationEmbeddable standardConfigurationEmbeddable = getStandardConfigurationEmbeddable(request);
        SecurityObject securityObject = securityObjectService.create
                (request.getName(),request.getZoneId(),addressEmbeddable,standardConfigurationEmbeddable);
        return ResponseEntity.status(HttpStatus.CREATED).body(securityObject);
    }



    @GetMapping("/{id}")
    public ResponseEntity<SecurityObject> findById(@PathVariable long id){
        return ResponseEntity.ok(securityObjectService.findById(id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<SecurityObject>> search(@RequestParam String name) {
        return ResponseEntity.ok(securityObjectService.search(name));
    }
    @GetMapping
    public ResponseEntity<List<SecurityObject>>findAll(){
        return ResponseEntity.ok(securityObjectService.findAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<SecurityObject> update(@PathVariable long id,
                                       @RequestBody SecurityObjectRequest request){
        return ResponseEntity.ok(securityObjectService.update(id,request.getName(),request.getZoneId(),getAddressEmbeddable(request),
                getStandardConfigurationEmbeddable(request)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        securityObjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
    private static StandardConfigurationEmbeddable getStandardConfigurationEmbeddable(SecurityObjectRequest request) {
        return new StandardConfigurationEmbeddable
                (request.getInspectionCount(), request.getInspectionDays(), request.getOpeningTime(), request.getOpeningDays(),
                        request.getClosingTime(), request.getClosingDays(), request.getInspectionWindowStart(), request.getInspectionWindowEnd(), request.getNotes());
    }

    private static AddressEmbeddable getAddressEmbeddable(SecurityObjectRequest request) {
        return new AddressEmbeddable(request.getStreet(), request.getCity(), request.getPostalCode());
    }
}
