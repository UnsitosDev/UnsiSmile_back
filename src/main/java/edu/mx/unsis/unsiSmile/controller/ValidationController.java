package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.ValidationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.ValidationResponse;
import edu.mx.unsis.unsiSmile.model.forms.questions.QuestionValidationModel;
import edu.mx.unsis.unsiSmile.service.QuestionValidationService;
import edu.mx.unsis.unsiSmile.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "VALIDATION")
@RestController
@RequestMapping("/unsismile/api/v1/validations")
@RequiredArgsConstructor
public class ValidationController {

    private final ValidationService validationService;
    private final QuestionValidationService questionValidationService;

    @Operation(summary = "Crea una nueva validación para las preguntas.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ValidationRequest request) {
        validationService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un tipo de validación por su id.")
    @GetMapping("/{id}")
    public ResponseEntity<ValidationResponse> findById(@PathVariable Long id) {
        ValidationResponse response = validationService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las validaciones")
    @GetMapping
    public ResponseEntity<List<ValidationResponse>> findAll() {
        List<ValidationResponse> response = validationService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una validación mediante su id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        validationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Crea la relación entre la pregunta y la validación.")
    @PostMapping("/question-validation")
    public ResponseEntity<QuestionValidationModel> createQuestionValidation(
            @RequestParam Long idQuestion,
            @RequestParam Long idValidation) {
        QuestionValidationModel response = questionValidationService.save(idQuestion, idValidation);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
