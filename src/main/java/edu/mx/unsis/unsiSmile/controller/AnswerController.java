package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.AnswerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.AnswerResponse;
import edu.mx.unsis.unsiSmile.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "---------------ANSWERS")
@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @Operation(summary = "Crea un registro respuesta, **necesita estar creada y relacionada con el paciente y el id de la pregunta a la que pertenece")
    @PostMapping
    public ResponseEntity<AnswerResponse> save(@RequestBody AnswerRequest request) {
        AnswerResponse response = answerService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "obtiene una respuesta por su id")
    @GetMapping("/{id}")
    public ResponseEntity<AnswerResponse> findById(@PathVariable Long id) {
        AnswerResponse response = answerService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las respuestas, ** a futuro paginarlas para no tener problemas de procesamiento")
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

    @Operation(summary = "Obtiene todas las respuestas que corresponden a una hc de un paciente")
    @GetMapping("/patient-clinical-history/{patientClinicalHistoryId}")
    public ResponseEntity<List<AnswerResponse>> getAnswersByPatientClinicalHistory(
            @PathVariable Long patientClinicalHistoryId) {
        List<AnswerResponse> response = answerService.getAnswersByPatientClinicalHistory(patientClinicalHistoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
