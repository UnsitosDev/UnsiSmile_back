package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.TreatmentReportResponse;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentDetailService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentReportService;
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

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Treatment Details")
@RestController
@RequestMapping("/unsismile/api/v1/treatment-details")
@RequiredArgsConstructor
public class TreatmentDetailController {

    private final TreatmentDetailService treatmentDetailService;
    private final TreatmentReportService treatmentReportService;

    @Operation(summary = "Crea un nuevo tratamiento para un paciente.")
    @PostMapping
    public ResponseEntity<TreatmentDetailResponse> createTreatmentDetail(@Valid @RequestBody TreatmentDetailRequest request) {
        TreatmentDetailResponse response = treatmentDetailService.createTreatmentDetail(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene el tratamiento de un paciente por el ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentDetailResponse> getTreatmentDetailById(@PathVariable Long id) {
        TreatmentDetailResponse response = treatmentDetailService.getTreatmentDetailById(id);
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

        Page<TreatmentDetailResponse> treatmentDetails = treatmentDetailService.getAllTreatmentDetailsByPatient(pageable, patientId);

        return ResponseEntity.ok(treatmentDetails);
    }

    @Operation(summary = "Actualiza un tratamiento de un paciente.")
    @PatchMapping("/{id}")
    public ResponseEntity<TreatmentDetailResponse> updateTreatmentDetail(
            @PathVariable Long id,
            @RequestBody TreatmentDetailRequest request) {
        TreatmentDetailResponse response = treatmentDetailService.updateTreatmentDetail(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina un tratamiento de un paciente por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatmentDetail(@PathVariable Long id) {
        treatmentDetailService.deleteTreatmentDetail(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene los tratamientos de todos los pacientes asignados a un alumno.")
    @GetMapping("/students/{idStudent}")
    public ResponseEntity<Page<TreatmentDetailResponse>> getAllTreatmentDetailsByStudent(
            @PathVariable String idStudent,
            @Parameter(description = "ID del tipo de tratamiento, ejemplo: 1  para las resinas")
            @RequestParam(required = false) Long idTreatment,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for ordering", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> treatmentDetails = treatmentDetailService.getAllTreatmentDetailsByStudent(pageable, idStudent, idTreatment);

        return ResponseEntity.ok(treatmentDetails);
    }

    @Operation(summary = "Envía un tratamiento a revisión")
    @PatchMapping("/{id}/revision")
    public ResponseEntity<TreatmentDetailResponse> sendToReview(
            @PathVariable Long id,
            @RequestParam Long professorClinicalAreaId) {

        TreatmentDetailResponse response = treatmentDetailService.sendToReview(id, professorClinicalAreaId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualiza el estado de un tratamiento (Finalizado/Rechazado)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<TreatmentDetailResponse> statusTreatment(
            @PathVariable Long id,
            @RequestParam ReviewStatus status) {

        TreatmentDetailResponse response = treatmentDetailService.statusTreatment(id, status);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener los tratamientos en revisión asignados a un profesor")
    @GetMapping("/professors/{professorId}")
    public ResponseEntity<Page<TreatmentDetailResponse>> getPatientsWithTreatmentsInReview(
            @PathVariable String professorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> response = treatmentDetailService.getTreatmentsInReviewByProfessor(
                professorId, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<TreatmentReportResponse>> getTreatmentReport(
            @RequestParam String enrollment,
            @RequestParam(required = false) Long semester,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "FINISHED") ReviewStatus status
    ) {
        List<TreatmentReportResponse> report = treatmentDetailService.getReport(enrollment, semester, startDate, endDate, status);
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Genera un reporte PDF de tratamientos para un estudiante")
    @GetMapping("/reports/students/{idStudent}")
    public ResponseEntity<byte[]> getTreatmentReportByStudent(
            @PathVariable String idStudent,
            @RequestParam(required = false) Long idTreatment) {
        return treatmentReportService.generateTreatmentReportByStudent(idStudent, idTreatment);
    }

    @Operation(summary = "Obtiene los tratamientos de todos los pacientes asignados a un alumno para reportes.")
    @GetMapping("/students/report/{idStudent}")
    public ResponseEntity<Page<TreatmentDetailResponse>> getAllTreatmentDetailsByStudentForReport(
            @PathVariable String idStudent,
            @Parameter(description = "ID del tipo de tratamiento, ejemplo: 1  para las resinas")
            @RequestParam(required = false) Long idTreatment,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for ordering", example = "idTreatmentDetail")
            @RequestParam(defaultValue = "idTreatmentDetail") String order,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> treatmentDetails = treatmentDetailService.getAllTreatmentDetailsByStudentForReport(pageable, idStudent, idTreatment);

        return ResponseEntity.ok(treatmentDetails);
    }
}