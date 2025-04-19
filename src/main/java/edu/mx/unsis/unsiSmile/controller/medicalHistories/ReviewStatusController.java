package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ReviewStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ReviewStatusResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
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

@Tag(name = "Review Status of Clinical Histories", description = "Gestiona el proceso de revisión y las actualizaciones del estado de las secciones de la historia clínica")
@RestController
@RequiredArgsConstructor
@RequestMapping("/unsismile/api/v1/medical-histories/status")
public class ReviewStatusController {

    private final ReviewStatusService reviewStatusService;

    @Operation(summary = "Actualizar el estado de la historia clínica")
    @PatchMapping
    public ResponseEntity<ReviewStatusResponse> updateReviewStatus(@Valid
            @RequestBody ReviewStatusRequest request) {
        reviewStatusService.updateReviewStatus(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Enviar una historia clínica a revisión")
    @PostMapping("/send-to-review/{idPatientClinicalHistory}/{idSection}/{idProfessorClinicalArea}")
    public ResponseEntity<Void> sendToReview(
            @PathVariable Long idPatientClinicalHistory,
            @PathVariable Long idSection,
            @PathVariable Long idProfessorClinicalArea) {
        reviewStatusService.sendToReview(idPatientClinicalHistory, idSection, idProfessorClinicalArea);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un listado de pacientes con una HC en un determinado estado de revisión.")
    @GetMapping("/list")
    public ResponseEntity<Page<PatientResponse>> getPatientsByReviewStatus(
            @RequestParam(defaultValue = "IN_REVIEW") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for sorting", example = "patientClinicalHistory.idPatientClinicalHistory")
            @RequestParam(defaultValue = "patientClinicalHistory.idPatientClinicalHistory") String order,
            @RequestParam(defaultValue = "true") boolean asc){

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest .of(page, size, sort);

        Page<PatientResponse> response = reviewStatusService.getPatientsByReviewStatus(
                status, pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene una lista de HC de un paciente en un determinado estado de revisión.")
    @GetMapping("/patient-clinical-histories")
    public ResponseEntity<List<PatientClinicalHistoryResponse>> searchClinicalHistory(@RequestParam String idPatient,
                                                                                      @RequestParam String status) {
        List<PatientClinicalHistoryResponse> response = reviewStatusService.searchClinicalHistory(idPatient, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtener el estado de revisión de la historia clínica por ID del paciente e ID de la sección")
    @GetMapping("/{idPatientClinicalHistory}/{idSection}")
    public ResponseEntity<ReviewStatusResponse> getStatusByPatientClinicalHistoryId(
            @PathVariable Long idPatientClinicalHistory,
            @PathVariable Long idSection) {
        ReviewStatusResponse response = reviewStatusService.getStatusByPatientClinicalHistoryId(
                idPatientClinicalHistory,
                idSection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
