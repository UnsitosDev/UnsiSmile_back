package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentDetailToothRequest;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentStatusUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentReportResponse;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentDetailService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentGeneralReportService;
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
import org.springframework.format.annotation.DateTimeFormat;
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
    private final TreatmentGeneralReportService treatmentGeneralReportService;

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
            @RequestParam Long professorClinicalAreaId,
            @RequestBody(required = false) TreatmentDetailToothRequest toothRequest) {
        TreatmentDetailResponse response = treatmentDetailService.sendToReview(id, professorClinicalAreaId, toothRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualiza el estado de un tratamiento (Finalizado/Rechazado/Cancelado)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<TreatmentDetailResponse> statusTreatment(
            @PathVariable Long id,
            @RequestBody TreatmentStatusUpdateRequest request) {

        TreatmentDetailResponse response = treatmentDetailService.updateTreatmentStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener los tratamientos asignados a un profesor, por estatus de tratamiento.")
    @GetMapping("/professors/{professorId}")
    public ResponseEntity<Page<TreatmentDetailResponse>> getPatientsWithTreatmentsInReview(
            @PathVariable String professorId,
            @Parameter(description = "Status de revisión", example = "FINISHED")
            @RequestParam(defaultValue = "IN_REVIEW") ReviewStatus reviewStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> response = treatmentDetailService.getTreatmentsInReviewByProfessor(
                professorId, reviewStatus, pageable);

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

    @Operation(summary = "Genera un reporte general en PDF de todos los tratamientos realizados (histórico o en un rango de fechas), opcional por alumno.")
    @GetMapping("/reports/general")
    public ResponseEntity<byte[]> getGeneralTreatmentReport(
            @RequestParam(required = false) String enrollment,
            @RequestParam(required = false)
            @Parameter(description = "Format dd-mm-yyyy")
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(required = false)
            @Parameter(description = "Format dd-mm-yyyy")
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate
    ) {
        return treatmentGeneralReportService.generateGeneralTreatmentReport(enrollment, startDate, endDate);
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

    @Operation(summary = "Obtiene los tratamientos relacionados con aprobación por parte de un profesor (pendientes, aprobados o rechazados).")
    @GetMapping("/professors/{professorId}/to-approve")
    public ResponseEntity<Page<TreatmentDetailResponse>> getTreatmentsToApprove(
            @PathVariable String professorId,
            @RequestParam(defaultValue = "AWAITING_APPROVAL") ReviewStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TreatmentDetailResponse> response = treatmentDetailService
                .getTreatmentsByProfessorAndStatus(professorId, status, pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Aprueba o rechaza el inicio un tratamiento")
    @PatchMapping("/{id}/approval")
    public ResponseEntity<TreatmentDetailResponse> approveOrRejectTreatment(
            @PathVariable Long id,
            @RequestBody @Valid TreatmentStatusUpdateRequest request) {

        TreatmentDetailResponse response = treatmentDetailService.approveOrRejectTreatment(id, request);
        return ResponseEntity.ok(response);
    }

}