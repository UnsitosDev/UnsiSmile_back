package edu.mx.unsis.unsiSmile.controller.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.components.FormComponentToothConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.components.FormComponentToothConditionResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.teeth.FormComponentToothConditionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Form Component Tooth Conditions")
@RestController
@RequestMapping("/unsismile/api/v1/medical-records/teeth/form-component-tooth-conditions")
@RequiredArgsConstructor
public class FormComponentToothConditionController {

    private final FormComponentToothConditionService service;

    @PostMapping
    public ResponseEntity<FormComponentToothConditionResponse> create(@Valid @RequestBody FormComponentToothConditionRequest request) {
        FormComponentToothConditionResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormComponentToothConditionResponse> getById(@PathVariable Long id) {
        FormComponentToothConditionResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FormComponentToothConditionResponse>> getAll() {
        List<FormComponentToothConditionResponse> responses = service.getAll();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormComponentToothConditionResponse> update(@PathVariable Long id, @Valid @RequestBody FormComponentToothConditionRequest request) {
        FormComponentToothConditionResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}