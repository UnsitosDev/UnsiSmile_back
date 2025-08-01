package edu.mx.unsis.unsiSmile.controller.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.CycleRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.CycleResponse;
import edu.mx.unsis.unsiSmile.service.students.CycleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/cycles")
public class CycleController {

    private final CycleService cycleService;

    public CycleController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @PostMapping
    public ResponseEntity<Void> createCycle(@RequestBody CycleRequest cycleRequest) {
        cycleService.createCycle(cycleRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CycleResponse> getCycleById(@PathVariable Long id) {
        CycleResponse cycleResponse = cycleService.getCycleById(id);
        return ResponseEntity.ok(cycleResponse);
    }

    @GetMapping
    public ResponseEntity<List<CycleResponse>> getAllCycles() {
        List<CycleResponse> allCycles = cycleService.getAllCycles();
        return ResponseEntity.ok(allCycles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCycle(@PathVariable Long id,
            @RequestBody CycleRequest updateCycleRequest) {
        cycleService.updateCycle(id, updateCycleRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCycleById(@PathVariable Long id) {
        cycleService.deleteCycleById(id);
        return ResponseEntity.noContent().build();
    }
}