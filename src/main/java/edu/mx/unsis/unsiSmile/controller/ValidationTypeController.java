package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.forms.questions.ValidationTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.questions.ValidationTypeResponse;
import edu.mx.unsis.unsiSmile.service.forms.questions.ValidationTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "VALIDATION TYPE")
@RestController
@RequestMapping("/unsismile/api/v1/validation-types")
@RequiredArgsConstructor
public class ValidationTypeController {

    private final ValidationTypeService validationTypeService;

    @Operation(summary = "Crea un tipo de validación ejemplo MAX_VALUE.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ValidationTypeRequest request) {
        validationTypeService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un tipo de validación por su id.")
    @GetMapping("/{id}")
    public ResponseEntity<ValidationTypeResponse> findById(@PathVariable Long id) {
        ValidationTypeResponse response = validationTypeService.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve todos los tipos de validación")
    @GetMapping
    public ResponseEntity<List<ValidationTypeResponse>> findAll() {
        List<ValidationTypeResponse> response = validationTypeService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un tipo de validación mediante su id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        validationTypeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
