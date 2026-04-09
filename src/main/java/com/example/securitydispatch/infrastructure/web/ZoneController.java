package com.example.securitydispatch.infrastructure.web;
import com.example.securitydispatch.application.ZoneService;
import com.example.securitydispatch.domain.Zone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/zones")
public class ZoneController {
    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }
    @PostMapping
    public ResponseEntity<Zone> create(@RequestBody ZoneRequest request){
        Zone zone = zoneService.create(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(zone);
    }
    @GetMapping("{id}")
    public ResponseEntity<Zone> findById(@PathVariable long id){
        return ResponseEntity.ok(zoneService.findById(id));
    }
    @GetMapping
    public ResponseEntity<List<Zone>>findAll(){
        return ResponseEntity.ok(zoneService.findAll());
    }
    @GetMapping("/search")
    public ResponseEntity<List<Zone>> search(@RequestParam String name) {
        return ResponseEntity.ok(zoneService.search(name));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Zone> update(@PathVariable long id,
                                       @RequestBody ZoneRequest request){
        return ResponseEntity.ok(zoneService.update(id,request.getName()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        zoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
