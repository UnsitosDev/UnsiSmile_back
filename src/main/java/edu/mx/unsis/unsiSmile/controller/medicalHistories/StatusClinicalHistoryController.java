package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.StatusClinicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.StatusClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.StatusClinicalHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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

    @Operation(summary = "Obtener el estado de la historia clínica por ID del paciente_historia_clínica")
    @GetMapping("/{idPatientClinicalHistory}")
    public ResponseEntity<StatusClinicalHistoryResponse> getStatusByPatientClinicalHistoryId(
            @PathVariable Long idPatientClinicalHistory) {
        StatusClinicalHistoryResponse response = statusClinicalHistoryService.getStatusByPatientClinicalHistoryId(idPatientClinicalHistory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar el estado de la historia clínica por ID del paciente_historia_clínica")
    @DeleteMapping("/{idPatientClinicalHistory}")
    public ResponseEntity<Void> deleteStatusByPatientClinicalHistoryId(
            @PathVariable Long idPatientClinicalHistory) {
        statusClinicalHistoryService.deleteStatusByPatientClinicalHistoryId(idPatientClinicalHistory);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Enviar una historia clínica a revisión")
    @PutMapping("/sendToReview/{idPatientClinicalHistory}")
    public ResponseEntity<Void> sendToReview(@PathVariable Long idPatientClinicalHistory) {
        statusClinicalHistoryService.sendToReview(idPatientClinicalHistory);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
