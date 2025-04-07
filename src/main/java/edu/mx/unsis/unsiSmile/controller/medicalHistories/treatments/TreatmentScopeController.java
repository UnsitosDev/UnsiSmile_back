package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentScopeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentScopeResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentScopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/treatment-scopes")
@RequiredArgsConstructor
public class TreatmentScopeController {

    private final TreatmentScopeService treatmentScopeService;

    @PostMapping
    public ResponseEntity<TreatmentScopeResponse> createTreatmentScope(@RequestBody TreatmentScopeRequest request) {
        TreatmentScopeResponse response = treatmentScopeService.createTreatmentScope(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentScopeResponse> getTreatmentScopeById(@PathVariable Long id) {
        TreatmentScopeResponse response = treatmentScopeService.getTreatmentScopeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TreatmentScopeResponse>> getAllTreatmentScopes() {
        List<TreatmentScopeResponse> responses = treatmentScopeService.getAllTreatmentScopes();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-type/{scopeTypeId}")
    public ResponseEntity<List<TreatmentScopeResponse>> getTreatmentScopesByType(@PathVariable Long scopeTypeId) {
        List<TreatmentScopeResponse> responses = treatmentScopeService.getTreatmentScopesByType(scopeTypeId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentScopeResponse> updateTreatmentScope(
            @PathVariable Long id,
            @RequestBody TreatmentScopeRequest request) {
        TreatmentScopeResponse response = treatmentScopeService.updateTreatmentScope(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentScopeById(@PathVariable Long id) {
        treatmentScopeService.deleteTreatmentScopeById(id);
        return ResponseEntity.noContent().build();
    }
}