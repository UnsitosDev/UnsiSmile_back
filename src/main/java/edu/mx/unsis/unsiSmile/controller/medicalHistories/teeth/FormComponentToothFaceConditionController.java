package edu.mx.unsis.unsiSmile.controller.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.components.FormComponentToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.components.FormComponentToothFaceConditionResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.teeth.FormComponentToothFaceConditionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Form Component Tooth Face Conditions")
@RestController
@RequestMapping("/unsismile/api/v1/medical-records/teeth/form-component-tooth-face-conditions")
@RequiredArgsConstructor
public class FormComponentToothFaceConditionController {

    private final FormComponentToothFaceConditionService service;

    @PostMapping
    public ResponseEntity<FormComponentToothFaceConditionResponse> create(@Valid @RequestBody FormComponentToothFaceConditionRequest request) {
        FormComponentToothFaceConditionResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormComponentToothFaceConditionResponse> getById(@PathVariable Long id) {
        FormComponentToothFaceConditionResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FormComponentToothFaceConditionResponse>> getAll() {
        List<FormComponentToothFaceConditionResponse> responses = service.getAll();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormComponentToothFaceConditionResponse> update(@PathVariable Long id, @Valid @RequestBody FormComponentToothFaceConditionRequest request) {
        FormComponentToothFaceConditionResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
