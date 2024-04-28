package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.patients.ReligionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.ReligionResponse;
import edu.mx.unsis.unsiSmile.service.patients.ReligionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/patients/religion")
public class ReligionController {

    private final ReligionService religionService;

    public ReligionController(ReligionService religionService) {
        this.religionService = religionService;
    }

    @PostMapping
    public ResponseEntity<ReligionResponse> createReligion(@Valid @RequestBody ReligionRequest religionRequest) {
        ReligionResponse createdReligion = religionService.createReligion(religionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReligion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReligionResponse> getReligionById(@Valid @PathVariable Long id) {
        ReligionResponse religionResponse = religionService.getReligionById(id);
        return ResponseEntity.ok(religionResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ReligionResponse> getReligionByName(@Valid @PathVariable String name) {
        ReligionResponse religionResponse = religionService.getReligionByName(name);
        return ResponseEntity.ok(religionResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReligionResponse>> getAllReligions() {
        List<ReligionResponse> allReligions = religionService.getAllReligions();
        return ResponseEntity.ok(allReligions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReligionResponse> updateReligion(@Valid @PathVariable Long id, @Valid @RequestBody ReligionRequest updatedReligionRequest) {
        ReligionResponse updatedReligion = religionService.updateReligion(id, updatedReligionRequest);
        return ResponseEntity.ok(updatedReligion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReligionById(@Valid @PathVariable Long id) {
        religionService.deleteReligionById(id);
        return ResponseEntity.noContent().build();
    }
}