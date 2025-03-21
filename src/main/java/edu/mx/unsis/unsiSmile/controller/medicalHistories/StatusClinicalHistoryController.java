package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.StatusClinicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.StatusClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.StatusClinicalHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "STATUS_CLINICAL_HISTORIES")
@RestController
@RequiredArgsConstructor
@RequestMapping("/unsismile/api/v1/medicalHistories/status")
public class StatusClinicalHistoryController {

    private final StatusClinicalHistoryService statusClinicalHistoryService;

    @Operation(summary = "Crear o actualizar el estado de la historia clínica")
    @PostMapping
    public ResponseEntity<StatusClinicalHistoryResponse> createOrUpdateStatusClinicalHistory(
            @RequestBody StatusClinicalHistoryRequest request) {
        statusClinicalHistoryService.createOrUpdateStatusClinicalHistory(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener el estado de la historia clínica por ID del paciente e ID de la sección")
    @GetMapping("/{idPatientClinicalHistory}/{idSection}")
    public ResponseEntity<StatusClinicalHistoryResponse> getStatusByPatientClinicalHistoryId(
            @PathVariable Long idPatientClinicalHistory,
            @PathVariable Long idSection) {
        StatusClinicalHistoryResponse response = statusClinicalHistoryService.getStatusByPatientClinicalHistoryId(
                idPatientClinicalHistory,
                idSection);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Enviar una historia clínica a revisión")
    @PutMapping("/sendToReview/{idPatientClinicalHistory}/{idSection}")
    public ResponseEntity<Void> sendToReview(
            @PathVariable Long idPatientClinicalHistory,
            @PathVariable Long idSection) {
        statusClinicalHistoryService.sendToReview(idPatientClinicalHistory, idSection);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un listado paginado de historias clínicas por estado")
    @GetMapping("/list")
    public ResponseEntity<Page<PatientResponse>> getStatusClinicalHistoriesByStatus(
            @RequestParam String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for sorting", example = "patientClinicalHistory.idPatientClinicalHistory")
            @RequestParam(defaultValue = "patientClinicalHistory.idPatientClinicalHistory") String order,
            @RequestParam(defaultValue = "true") boolean asc){

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest .of(page, size, sort);

        Page<PatientResponse> response = statusClinicalHistoryService.getStatusClinicalHistoriesByStatus(
                status, pageable);

        return ResponseEntity.ok(response);
    }
}
