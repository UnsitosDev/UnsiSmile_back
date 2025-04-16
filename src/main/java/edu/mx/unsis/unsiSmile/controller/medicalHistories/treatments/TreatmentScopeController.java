package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentScopeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentScopeResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentScopeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Treatment Scopes")
@RestController
@RequestMapping("/unsismile/api/v1/treatment-scopes")
@RequiredArgsConstructor
public class TreatmentScopeController {

    private final TreatmentScopeService treatmentScopeService;

    @Operation(summary = "Crea un nuevo alcance de tratamiento")
    @PostMapping
    public ResponseEntity<TreatmentScopeResponse> createTreatmentScope(@Valid @RequestBody TreatmentScopeRequest request) {
        TreatmentScopeResponse response = treatmentScopeService.createTreatmentScope(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un alcance de tratamiento por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentScopeResponse> getTreatmentScopeById(@PathVariable Long id) {
        TreatmentScopeResponse response = treatmentScopeService.getTreatmentScopeById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene todos los alcances de tratamiento disponibles")
    @GetMapping
    public ResponseEntity<List<TreatmentScopeResponse>> getAllTreatmentScopes() {
        List<TreatmentScopeResponse> responses = treatmentScopeService.getAllTreatmentScopes();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtiene los alcances de tratamiento asociados a un tipo de alcance")
    @GetMapping("/by-type/{scopeTypeId}")
    public ResponseEntity<List<TreatmentScopeResponse>> getTreatmentScopesByType(@PathVariable Long scopeTypeId) {
        List<TreatmentScopeResponse> responses = treatmentScopeService.getTreatmentScopesByType(scopeTypeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Actualiza un alcance de tratamiento existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<TreatmentScopeResponse> updateTreatmentScope(
            @PathVariable Long id,
            @RequestBody TreatmentScopeRequest request) {
        TreatmentScopeResponse response = treatmentScopeService.updateTreatmentScope(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina un alcance de tratamiento por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentScopeById(@PathVariable Long id) {
        treatmentScopeService.deleteTreatmentScopeById(id);
        return ResponseEntity.noContent().build();
    }
}