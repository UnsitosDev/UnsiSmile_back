package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.OccupationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.OccupationResponse;
import edu.mx.unsis.unsiSmile.service.patients.OccupationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Obtener una lista paginada de ocupaciones")
    @GetMapping
    public ResponseEntity<Page<OccupationResponse>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "occupation") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OccupationResponse> occupationResponses = occupationService.getAllOccupations(pageable);
        return ResponseEntity.ok(occupationResponses);
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
