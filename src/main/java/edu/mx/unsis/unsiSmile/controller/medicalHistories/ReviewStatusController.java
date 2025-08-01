package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.forms.sections.ReviewStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientMedicalRecordResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ReviewStatusResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ReviewStatusService;
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

import java.util.List;

@Tag(name = "Estado de revisión de las historias clínicas", description = "Gestiona el proceso de revisión y las actualizaciones del estado de las secciones de la historia clínica")
@RestController
@RequiredArgsConstructor
@RequestMapping("/unsismile/api/v1/medical-records/status")
public class ReviewStatusController {

    private final ReviewStatusService reviewStatusService;

    @Operation(summary = "Actualizar el estado de la historia clínica (aceptar sections)")
    @PatchMapping
    public ResponseEntity<ReviewStatusResponse> updateReviewStatus(@Valid
            @RequestBody ReviewStatusRequest request) {
        reviewStatusService.updateReviewStatus(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Enviar una historia clínica a revisión (sections)")
    @PostMapping("/{patientMedicalRecordId}/sections/{sectionId}/review/{professorClinicalAreaId}")
    public ResponseEntity<Void> sendToReview(
            @PathVariable Long patientMedicalRecordId,
            @PathVariable String sectionId,
            @PathVariable Long professorClinicalAreaId) {
        reviewStatusService.sendToReview(patientMedicalRecordId, sectionId, professorClinicalAreaId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un listado de secciones en revisión asignados a un profesor.")
    @GetMapping("/review/assigned-sections")
    public ResponseEntity<Page<ReviewStatusResponse>> getReviewStatusByStatus(
            @RequestParam(defaultValue = "IN_REVIEW") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for sorting", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "false") boolean asc){

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest .of(page, size, sort);

        Page<ReviewStatusResponse> response = reviewStatusService.getReviewStatusByStatus(
                status, pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene una lista de HC de un paciente en un determinado estado de revisión.")
    @GetMapping("/patients")
    public ResponseEntity<List<PatientMedicalRecordResponse>> searchMedicalRecords(@RequestParam String idPatient,
                                                                                   @RequestParam String status) {
        List<PatientMedicalRecordResponse> response = reviewStatusService.searchMedicalRecords(idPatient, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtener el estado de revisión de la historia clínica por ID del paciente e ID de la sección")
    @GetMapping("/{idPatientMedicalRecord}/{idSection}")
    public ResponseEntity<ReviewStatusResponse> getStatusByPatientMedicalRecordId(
            @PathVariable Long idPatientMedicalRecord,
            @PathVariable String idSection) {
        ReviewStatusResponse response = reviewStatusService.getStatusByPatientMedicalRecordId(
                idPatientMedicalRecord,
                idSection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
