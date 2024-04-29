package edu.mx.unsis.unsiSmile.controller.patients;

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

import edu.mx.unsis.unsiSmile.dtos.request.patients.OccupationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.OccupationResponse;
import edu.mx.unsis.unsiSmile.service.patients.OccupationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/patients/occupations")
public class OccupationController {

    private final OccupationService occupationService;

    public OccupationController(OccupationService occupationService) {
        this.occupationService = occupationService;
    }

    @PostMapping
    public ResponseEntity<OccupationResponse> createOccupation(@Valid @RequestBody OccupationRequest occupationRequest) {
        OccupationResponse createdOccupation = occupationService.createOccupation(occupationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOccupation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccupationResponse> getOccupationById(@Valid @PathVariable Long id) {
        OccupationResponse occupationResponse = occupationService.getOccupationById(id);
        return ResponseEntity.ok(occupationResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<OccupationResponse> getOccupationByName(@Valid @PathVariable String name) {
        OccupationResponse occupationResponse = occupationService.getOccupationByName(name);
        return ResponseEntity.ok(occupationResponse);
    }

    @GetMapping
    public ResponseEntity<List<OccupationResponse>> getAllOccupations() {
        List<OccupationResponse> allOccupations = occupationService.getAllOccupations();
        return ResponseEntity.ok(allOccupations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OccupationResponse> updateOccupation(@Valid @PathVariable Long id, @Valid @RequestBody OccupationRequest updatedOccupationRequest) {
        OccupationResponse updatedOccupation = occupationService.updateOccupation(id, updatedOccupationRequest);
        return ResponseEntity.ok(updatedOccupation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOccupationById(@Valid @PathVariable Long id) {
        occupationService.deleteOccupationById(id);
        return ResponseEntity.noContent().build();
    }
}
