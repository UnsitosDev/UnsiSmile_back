package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TREATMENTS")
@RestController
@RequestMapping("/unsismile/api/v1/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;

    @PostMapping
    public ResponseEntity<TreatmentResponse> createTreatment(@Valid @RequestBody TreatmentRequest request) {
        TreatmentResponse response = treatmentService.createTreatment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentResponse> getTreatmentById(@PathVariable Long id) {
        TreatmentResponse response = treatmentService.getTreatmentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TreatmentResponse>> getAllTreatments() {
        List<TreatmentResponse> responses = treatmentService.getAllTreatments();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-scope-type/{scopeTypeId}")
    public ResponseEntity<List<TreatmentResponse>> getTreatmentsByScope(@PathVariable Long scopeTypeId) {
        List<TreatmentResponse> responses = treatmentService.getTreatmentsByScopeType(scopeTypeId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-clinical-history/{catalogId}")
    public ResponseEntity<List<TreatmentResponse>> getTreatmentsByClinicalHistory(@PathVariable Long catalogId) {
        List<TreatmentResponse> responses = treatmentService.getTreatmentsByClinicalHistory(catalogId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentResponse> updateTreatment(
            @PathVariable Long id,
            @RequestBody TreatmentRequest request) {
        TreatmentResponse response = treatmentService.updateTreatment(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentById(@PathVariable Long id) {
        treatmentService.deleteTreatmentById(id);
        return ResponseEntity.noContent().build();
    }
}