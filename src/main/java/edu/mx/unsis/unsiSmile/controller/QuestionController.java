package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.forms.questions.QuestionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.questions.QuestionResponse;
import edu.mx.unsis.unsiSmile.service.forms.questions.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "QUESTION")
@RestController
@RequestMapping("/unsismile/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "Crea una pregunta para algun formulario.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody QuestionRequest request) {
        questionService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una pregunta por su id.")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> findById(@PathVariable Long id) {
        QuestionResponse response = questionService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve todas las preguntas.")
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> findAll() {
        List<QuestionResponse> response = questionService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una pregunta por su id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        questionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtiene todas las preguntas con o sin respuesta que pertenecen a un formulario mediante el id.")
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<QuestionResponse>> findAllBySection(
            @PathVariable String sectionId, @RequestParam(required = false) String patientId) {
        List<QuestionResponse> response = questionService.findAllBySection(sectionId, patientId, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
