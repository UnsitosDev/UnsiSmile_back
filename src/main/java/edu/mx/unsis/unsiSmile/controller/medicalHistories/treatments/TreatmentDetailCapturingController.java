package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailCapturingRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentStatusUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentDetailCapturingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Treatment Details Capturing", description = "Endpoints for capturing treatment details of patients")
@RestController
@RequestMapping("/unsismile/api/v1/treatment-details/capturing")
@RequiredArgsConstructor
public class TreatmentDetailCapturingController {

    private final TreatmentDetailCapturingService treatmentDetailCapturingService;

    @Operation(summary = "Crea un nuevo tratamiento para un paciente.")
    @PostMapping
    public ResponseEntity<TreatmentDetailResponse> createTreatmentDetail(@Valid @RequestBody TreatmentDetailCapturingRequest request) {
        TreatmentDetailResponse response = treatmentDetailCapturingService.createTreatmentDetail(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene el tratamiento de un paciente por el ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDetailResponse> getTreatmentDetailById(@PathVariable Long id) {
        TreatmentDetailResponse response = treatmentDetailCapturingService.getTreatmentDetailById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene todos los tratamientos de un paciente con paginación y ordenamiento")
    @GetMapping("/patients/{patientId}")
    public ResponseEntity<Page<TreatmentDetailResponse>> getAllTreatmentDetailsByPatient(
            @PathVariable String patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for ordering", example = "idTreatmentDetail")
            @RequestParam(defaultValue = "idTreatmentDetail") String order,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> treatmentDetails = treatmentDetailCapturingService.getAllTreatmentDetailsByPatient(pageable, patientId);

        return ResponseEntity.ok(treatmentDetails);
    }

    @Operation(summary = "Actualiza un tratamiento de un paciente.")
    @PatchMapping("/{id}")
    public ResponseEntity<TreatmentDetailResponse> updateTreatmentDetail(
            @PathVariable Long id,
            @RequestBody TreatmentDetailCapturingRequest request) {
        TreatmentDetailResponse response = treatmentDetailCapturingService.updateTreatmentDetail(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualiza el estado de un tratamiento (Finalizado/Cancelado)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<TreatmentDetailResponse> statusTreatment(
            @PathVariable Long id,
            @RequestBody TreatmentStatusUpdateRequest request) {

        TreatmentDetailResponse response = treatmentDetailCapturingService.updateTreatmentStatus(id, request);
        return ResponseEntity.ok(response);
    }
}