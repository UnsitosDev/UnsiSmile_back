package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.CycleRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CycleResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.service.CycleService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/cycles")
@Slf4j
public class CycleController {
    private final CycleService cycleService;

    public CycleController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @PostMapping
    public ResponseEntity<CycleResponse> createCycle(@RequestBody CycleRequest cycleRequest){
        try {
            CycleResponse createdCycle = cycleService.createCycle(cycleRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCycle);
        } catch (AppException ex) {
            log.error("Failed to update cycle: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CycleResponse> getCycleById(@PathVariable Long id){
        try{
            CycleResponse cycleResponse= cycleService.getCycleById(id);
            return ResponseEntity.ok(cycleResponse);
        }catch (AppException ex){
            log.error("Failed to fetch cycles: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CycleResponse>> getAllCycles(){
        try {
            List<CycleResponse> allCycles = cycleService.getAllCycles();
            return ResponseEntity.ok(allCycles);
        }catch (AppException ex){
            log.error("Failed to fetch cycles: {}",ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CycleResponse> updateCycle(@PathVariable Long id, @RequestBody CycleRequest updateCycleRequest){
        try{
            CycleResponse updateCycle = cycleService.updateCycle(id, updateCycleRequest);
            return ResponseEntity.ok(updateCycle);
        }catch (AppException ex){
            log.error("Failed to update cycle: {}",ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCycleById(@PathVariable Long id){
        try {
            cycleService.deleteCycleById(id);
            return ResponseEntity.noContent().build();
        }catch (AppException ex){
            log.error("Failed to delete cycle: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }
}
