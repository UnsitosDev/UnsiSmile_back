package edu.mx.unsis.unsiSmile.controller.patients.demographics;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.MaritalStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.MaritalStatusResponse;
import edu.mx.unsis.unsiSmile.service.patients.demographics.MaritalStatusService;
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
@RequestMapping("/unsismile/api/v1/marital-status")
public class MaritalStatusController {

    private final MaritalStatusService maritalStatusService;

    public MaritalStatusController(MaritalStatusService maritalStatusService) {
        this.maritalStatusService = maritalStatusService;
    }

    @PostMapping
    public ResponseEntity<MaritalStatusResponse> createMaritalStatus(@Valid @RequestBody MaritalStatusRequest maritalStatusRequest) {
        MaritalStatusResponse createdMaritalStatus = maritalStatusService.createMaritalStatus(maritalStatusRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaritalStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaritalStatusResponse> getMaritalStatusById(@Valid @PathVariable Long id) {
        MaritalStatusResponse maritalStatusResponse = maritalStatusService.getMaritalStatusById(id);
        return ResponseEntity.ok(maritalStatusResponse);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<MaritalStatusResponse> getMaritalStatusByStatus(@Valid @PathVariable String status) {
        MaritalStatusResponse maritalStatusResponse = maritalStatusService.getMaritalStatusByStatus(status);
        return ResponseEntity.ok(maritalStatusResponse);
    }

    @Operation(summary = "Obtener una lista paginada de estados civil")
    @GetMapping
    public ResponseEntity<Page<MaritalStatusResponse>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "maritalStatus") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MaritalStatusResponse> maritalStatusResponses = maritalStatusService.getAllMaritalStatus(pageable);
        return ResponseEntity.ok(maritalStatusResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaritalStatusResponse> updateMaritalStatus(@Valid @PathVariable Long id, @Valid @RequestBody MaritalStatusRequest updatedMaritalStatusRequest) {
        MaritalStatusResponse updatedMaritalStatus = maritalStatusService.updateMaritalStatus(id, updatedMaritalStatusRequest);
        return ResponseEntity.ok(updatedMaritalStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaritalStatusById(@Valid @PathVariable Long id) {
        maritalStatusService.deleteMaritalStatusById(id);
        return ResponseEntity.noContent().build();
    }
}