package com.example.securitydispatch.infrastructure.web;
import com.example.securitydispatch.application.SecurityObjectService;
import com.example.securitydispatch.application.ShiftService;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.SecurityObject;
import com.example.securitydispatch.domain.Shift;
import com.example.securitydispatch.domain.Zone;
import com.example.securitydispatch.infrastructure.persistence.AddressEmbeddable;
import com.example.securitydispatch.infrastructure.persistence.StandardConfigurationEmbeddable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/shifts")
public class ShiftController {
    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }
    @PostMapping
    public ResponseEntity<Shift> create(@RequestBody ShiftRequest request){
      Shift shift = shiftService.create(

              request.getDriverId(),
              request.getZoneId(),
              request.getDeploymentDate(),
              request.getStartTime(),
              request.getEndTime()
      );
        return ResponseEntity.status(HttpStatus.CREATED).body(shift);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Shift> findById(@PathVariable long id){
        return ResponseEntity.ok(shiftService.findById(id));
    }
    @GetMapping
    public ResponseEntity<List<Shift>>findAll(){
        return ResponseEntity.ok(shiftService.findAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        shiftService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

