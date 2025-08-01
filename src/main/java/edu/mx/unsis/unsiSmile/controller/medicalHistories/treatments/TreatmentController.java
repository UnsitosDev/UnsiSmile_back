package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Crea un nuevo tratamiento")
    @PostMapping
    public ResponseEntity<TreatmentResponse> createTreatment(@Valid @RequestBody TreatmentRequest request) {
        TreatmentResponse response = treatmentService.createTreatment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un tratamiento por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentResponse> getTreatmentById(@PathVariable Long id) {
        TreatmentResponse response = treatmentService.getTreatmentById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene todos los tratamientos disponibles")
    @GetMapping
    public ResponseEntity<List<TreatmentResponse>> getAllTreatments() {
        List<TreatmentResponse> responses = treatmentService.getAllTreatments();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtiene los tratamientos asociados a un tipo de alcance")
    @GetMapping("/scopes/{treatmentScopeId}")
    public ResponseEntity<List<TreatmentResponse>> getTreatmentsByScope(@PathVariable Long treatmentScopeId) {
        List<TreatmentResponse> responses = treatmentService.getTreatmentsByTreatmentScope(treatmentScopeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtiene los tratamientos asociados a una historia clínica")
    @GetMapping("/medical-records/{medicalRecordId}")
    public ResponseEntity<List<TreatmentResponse>> getTreatmentsByMedicalRecord(@PathVariable Long medicalRecordId) {
        List<TreatmentResponse> responses = treatmentService.getTreatmentsByMedicalRecord(medicalRecordId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Actualiza un tratamiento existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<TreatmentResponse> updateTreatment(
            @PathVariable Long id,
            @RequestBody TreatmentRequest request) {
        TreatmentResponse response = treatmentService.updateTreatment(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina un tratamiento por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentById(@PathVariable Long id) {
        treatmentService.deleteTreatmentById(id);
        return ResponseEntity.noContent().build();
    }

}