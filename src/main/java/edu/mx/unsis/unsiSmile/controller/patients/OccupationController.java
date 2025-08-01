package edu.mx.unsis.unsiSmile.controller.patients;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.OccupationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.OccupationResponse;
import edu.mx.unsis.unsiSmile.service.patients.OccupationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/occupations")
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

    @Operation(summary = "Obtener una lista paginada de ocupaciones")
    @GetMapping
    public ResponseEntity<Page<OccupationResponse>> getAllOccupations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "occupation") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(required = false) String keyword) {
        Page<OccupationResponse> occupations = 
            occupationService.getAllOccupations(page, size, order, asc, keyword);
        return ResponseEntity.ok(occupations);
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
