package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.forms.answers.AnswerTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.answers.AnswerTypeResponse;
import edu.mx.unsis.unsiSmile.service.AnswerTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ANSWER TYPES")
@RestController
@RequestMapping("/unsismile/api/v1/answer-types")
@RequiredArgsConstructor
public class AnswerTypeController {

    private final AnswerTypeService answerTypeService;

    @Operation(summary = "Crea un tipo de respuesta.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AnswerTypeRequest request) {
        answerTypeService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un tipo de respuesta mediante su id")
    @GetMapping("/{id}")
    public ResponseEntity<AnswerTypeResponse> findById(@PathVariable Long id) {
        AnswerTypeResponse response = answerTypeService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todos los tipos de respuestas")
    @GetMapping
    public ResponseEntity<List<AnswerTypeResponse>> findAll() {
        List<AnswerTypeResponse> response = answerTypeService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un tipo de respuesta por su id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        answerTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
