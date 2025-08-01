package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentScopeRequest;
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

@Tag(name = "SCOPE TYPES FOR TREATMENTS")
@RestController
@RequestMapping("/unsismile/api/v1/treatment-scopes")
@RequiredArgsConstructor
public class TreatmentScopeController {

    private final TreatmentScopeService treatmentScopeService;

    @Operation(summary = "Crea un nuevo tipo de alcance (para los tratamientos).")
    @PostMapping
    public ResponseEntity<TreatmentScopeResponse> createTreatmentScope(@Valid @RequestBody TreatmentScopeRequest TreatmentScopeRequest) {
        TreatmentScopeResponse response = treatmentScopeService.createTreatmentScope(TreatmentScopeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un alcance de tratamiento por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentScopeResponse> getTreatmentScopeById(@PathVariable Long id) {
        TreatmentScopeResponse response = treatmentScopeService.getTreatmentScopeById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene todos los tipos de alcance de tratamientos disponibles")
    @GetMapping
    public ResponseEntity<List<TreatmentScopeResponse>> getAllTreatmentScopes() {
        List<TreatmentScopeResponse> responses = treatmentScopeService.getAllTreatmentScopes();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Actualiza un alcance de tratamiento existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<TreatmentScopeResponse> updateTreatmentScope(
            @PathVariable Long id,
            @RequestBody TreatmentScopeRequest updateTreatmentScopeRequest) {
        TreatmentScopeResponse response = treatmentScopeService.updateTreatmentScope(id, updateTreatmentScopeRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina un alcance de tratamiento por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentScopeById(@PathVariable Long id) {
        treatmentScopeService.deleteTreatmentScopeById(id);
        return ResponseEntity.noContent().build();
    }
}