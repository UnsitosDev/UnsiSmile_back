package edu.mx.unsis.unsiSmile.controller.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.components.FormComponentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.components.FormComponentResponse;
import edu.mx.unsis.unsiSmile.service.medicalrecords.components.FormComponentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Form components")
@RestController
@RequestMapping("/unsismile/api/v1/medical-records/teeth/form-components")
@RequiredArgsConstructor
public class FormComponentController {

    private final FormComponentService formComponentService;

    @PostMapping
    public ResponseEntity<FormComponentResponse> createFormComponent(@Valid @RequestBody FormComponentRequest request) {
        FormComponentResponse createdFormComponentResponse = formComponentService.createFormComponent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFormComponentResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormComponentResponse> getFormComponentById(@Valid @PathVariable Long id) {
        FormComponentResponse formComponentResponse = formComponentService.getFormComponentById(id);
        return ResponseEntity.ok(formComponentResponse);
    }

    @GetMapping
    public ResponseEntity<List<FormComponentResponse>> getAllFormComponents() {
        List<FormComponentResponse> allFormComponents = formComponentService.getAllFormComponents();
        return ResponseEntity.ok(allFormComponents);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormComponentResponse> updateFormComponent(
            @Valid @PathVariable Long id,
            @Valid @RequestBody FormComponentRequest updateRequest) {
        FormComponentResponse updatedFormComponentResponse = formComponentService.updateFormComponent(id, updateRequest);
        return ResponseEntity.ok(updatedFormComponentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFormComponent(@Valid @PathVariable Long id) {
        formComponentService.deleteFormComponent(id);
        return ResponseEntity.noContent().build();
    }
}
