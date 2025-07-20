package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.request.AnswerUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.AnswerResponse;
import edu.mx.unsis.unsiSmile.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ANSWERS")
@RestController
@RequestMapping("/unsismile/api/v1/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @Operation(summary = "Guarda la respuesta a una pregunta.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AnswerRequest request) {
        answerService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una respuesta por su id.")
    @GetMapping("/{id}")
    public ResponseEntity<AnswerResponse> findById(@PathVariable Long id) {
        AnswerResponse response = answerService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las respuestas en el sistema.")
    @GetMapping
    public ResponseEntity<List<AnswerResponse>> findAll() {
        List<AnswerResponse> response = answerService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una respuesta por su id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        answerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Recupera todas las respuestas asociadas a la historia clínica de un paciente.")
    @GetMapping("/patient-clinical-history/{patientClinicalHistoryId}")
    public ResponseEntity<List<AnswerResponse>> getAnswersByPatientMedicalRecord(
            @PathVariable Long patientClinicalHistoryId) {
        List<AnswerResponse> response = answerService.getAnswersByPatientMedicalRecord(patientClinicalHistoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crea múltiples registros de respuestas, para rellenar los formularios.")
    @PostMapping("/forms")
    public ResponseEntity<Void> saveBatch(@RequestBody List<AnswerRequest> requests) {
        answerService.saveBatch(requests);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza múltiples registros de respuestas.")
    @PatchMapping("/forms")
    public ResponseEntity<Void> updateBatch(@RequestBody List<AnswerUpdateRequest> requests) {
        answerService.updateBatch(requests);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
