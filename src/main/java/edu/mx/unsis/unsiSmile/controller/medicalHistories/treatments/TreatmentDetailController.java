package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentDetailService;
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

@Tag(name = "Treatment Details")
@RestController
@RequestMapping("/unsismile/api/v1/treatment-details")
@RequiredArgsConstructor
public class TreatmentDetailController {

    private final TreatmentDetailService treatmentDetailService;

    @Operation(summary = "Crea un nuevo detalle de tratamiento")
    @PostMapping
    public ResponseEntity<TreatmentDetailResponse> createTreatmentDetail(@Valid @RequestBody TreatmentDetailRequest request) {
        TreatmentDetailResponse response = treatmentDetailService.createTreatmentDetail(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un detalle de tratamiento por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDetailResponse> getTreatmentDetailById(@PathVariable Long id) {
        TreatmentDetailResponse response = treatmentDetailService.getTreatmentDetailById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene todos los detalles de tratamiento de un paciente con paginación y ordenamiento")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<TreatmentDetailResponse>> getAllTreatmentDetailsByPatient(
            @PathVariable String patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for ordering", example = "idTreatmentDetail")
            @RequestParam(defaultValue = "idTreatmentDetail") String order,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> treatmentDetails = treatmentDetailService.getAllTreatmentDetailsByPatient(pageable, patientId);

        return ResponseEntity.ok(treatmentDetails);
    }

    @Operation(summary = "Actualiza un detalle de tratamiento existente")
    @PutMapping("/{id}")
    public ResponseEntity<TreatmentDetailResponse> updateTreatmentDetail(
            @PathVariable Long id,
            @RequestBody TreatmentDetailRequest request
    ) {
        TreatmentDetailResponse response = treatmentDetailService.updateTreatmentDetail(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina un detalle de tratamiento por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentDetail(@PathVariable Long id) {
        treatmentDetailService.deleteTreatmentDetail(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene todos los tratamientos de todos los pacientes asignados a un alumno.")
    @GetMapping("/students/{idStudent}")
    public ResponseEntity<Page<TreatmentDetailResponse>> getAllTreatmentDetailsByStudent(
            @PathVariable String idStudent,
            @RequestParam(required = false) Long idTreatment,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for ordering", example = "idTreatmentDetail")
            @RequestParam(defaultValue = "idTreatmentDetail") String order,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> treatmentDetails = treatmentDetailService.getAllTreatmentDetailsByStudent(pageable, idStudent, idTreatment);

        return ResponseEntity.ok(treatmentDetails);
    }
}