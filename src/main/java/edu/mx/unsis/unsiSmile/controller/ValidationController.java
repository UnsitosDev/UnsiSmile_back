package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.ValidationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.ValidationResponse;
import edu.mx.unsis.unsiSmile.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "---------VALIDATION")
@RestController
@RequestMapping("/api/validations")
@RequiredArgsConstructor
public class ValidationController {

    private final ValidationService validationService;

    @Operation(summary = "Crea una nueva validación para las preguntas, **necesita un mensaje, valor y un id del tipo de validaión , revisar todas las tablas de intersecciones muchos a muchos para ver como y donde se crean")
    @PostMapping
    public ResponseEntity<ValidationResponse> save(@RequestBody ValidationRequest request) {
        ValidationResponse response = validationService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un tipo de respuesta por su id")
    @GetMapping("/{id}")
    public ResponseEntity<ValidationResponse> findById(@PathVariable Long id) {
        ValidationResponse response = validationService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene todas las validaciones que existen, **paginar")
    @GetMapping
    public ResponseEntity<List<ValidationResponse>> findAll() {
        List<ValidationResponse> response = validationService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una validación mediante su id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        validationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
