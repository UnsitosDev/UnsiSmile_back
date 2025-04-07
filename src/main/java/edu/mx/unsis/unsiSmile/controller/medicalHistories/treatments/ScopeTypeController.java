package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.ScopeTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.ScopeTypeResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.ScopeTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SCOPE TYPES")
@RestController
@RequestMapping("/unsismile/api/v1/scope-types")
@RequiredArgsConstructor
public class ScopeTypeController {

    private final ScopeTypeService scopeTypeService;

    @PostMapping
    public ResponseEntity<ScopeTypeResponse> createScopeType(@Valid @RequestBody ScopeTypeRequest scopeTypeRequest) {
        ScopeTypeResponse response = scopeTypeService.createScopeType(scopeTypeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScopeTypeResponse> getScopeTypeById(@PathVariable Long id) {
        ScopeTypeResponse response = scopeTypeService.getScopeTypeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ScopeTypeResponse>> getAllScopeTypes() {
        List<ScopeTypeResponse> responses = scopeTypeService.getAllScopeTypes();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScopeTypeResponse> updateScopeType(
            @PathVariable Long id,
            @RequestBody ScopeTypeRequest updateScopeTypeRequest) {
        ScopeTypeResponse response = scopeTypeService.updateScopeType(id, updateScopeTypeRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScopeTypeById(@PathVariable Long id) {
        scopeTypeService.deleteScopeTypeById(id);
        return ResponseEntity.noContent().build();
    }
}