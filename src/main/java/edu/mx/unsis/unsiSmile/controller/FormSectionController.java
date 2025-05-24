package edu.mx.unsis.unsiSmile.controller;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FormSectionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.FormSectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FORM SECTION")
@RestController
@RequestMapping("/unsismile/api/v1/form-sections")
@RequiredArgsConstructor
public class FormSectionController {

    private final FormSectionService formSectionService;

    @Operation(summary = "Crea una sección para un formulario, puede o no ser una subsección.")
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody FormSectionRequest request) {
        formSectionService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una sección y la configuración con el id de la historia clínica del paciente.")
    @GetMapping("/{id}")
    public ResponseEntity<FormSectionResponse> findById(@PathVariable String id, @RequestParam Long idPatientClinicalHistory) {
        FormSectionResponse response = formSectionService.findById(id, idPatientClinicalHistory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve todas las secciones, sin configuración.")
    @GetMapping
    public ResponseEntity<List<FormSectionResponse>> findAll() {
        List<FormSectionResponse> response = formSectionService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una sección por su id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        formSectionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
