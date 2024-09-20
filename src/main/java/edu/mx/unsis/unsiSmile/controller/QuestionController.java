package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.QuestionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.QuestionResponse;
import edu.mx.unsis.unsiSmile.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "----------QUESTION")
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "Crea una pregunta para las secciones, **necesita la descripción de la pregunta, si es o no requerida, por ahora es obligatorio l sección a la que pertenece, y el tipo de respuesta")
    @PostMapping
    public ResponseEntity<QuestionResponse> save(@RequestBody QuestionRequest request) {
        QuestionResponse response = questionService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una pregunta con el id de esta")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> findById(@PathVariable Long id) {
        QuestionResponse response = questionService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve todas las preguntas")
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> findAll() {
        List<QuestionResponse> response = questionService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una pregunta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        questionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtiene todas las preguntas con o sin respuesta que pertenecen a una sección mediante el id de la sección")
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<QuestionResponse>> findAllBySection(
            @PathVariable Long sectionId, @RequestParam(required = false) Long patientClinicalHistoryId) {
        List<QuestionResponse> response = questionService.findAllBySection(sectionId, patientClinicalHistoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
