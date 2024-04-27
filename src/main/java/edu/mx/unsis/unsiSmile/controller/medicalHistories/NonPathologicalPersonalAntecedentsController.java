package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.NonPathologicalPersonalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.NonPathologicalPersonalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.NonPathologicalPersonalAntecedentsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/non-pathological-personal-antecedents")
public class NonPathologicalPersonalAntecedentsController {

    private final NonPathologicalPersonalAntecedentsService nonPathologicalPersonalAntecedentsService;

    public NonPathologicalPersonalAntecedentsController(NonPathologicalPersonalAntecedentsService nonPathologicalPersonalAntecedentsService) {
        this.nonPathologicalPersonalAntecedentsService = nonPathologicalPersonalAntecedentsService;
    }

    @PostMapping
    public ResponseEntity<NonPathologicalPersonalAntecedentsResponse> createNonPathologicalPersonalAntecedents(
            @Valid @RequestBody NonPathologicalPersonalAntecedentsRequest request) {
        NonPathologicalPersonalAntecedentsResponse createdNonPathologicalPersonalAntecedent = nonPathologicalPersonalAntecedentsService
                .createNonPathologicalPersonalAntecedents(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNonPathologicalPersonalAntecedent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NonPathologicalPersonalAntecedentsResponse> getNonPathologicalPersonalAntecedentsById(
            @Valid @PathVariable Long id) {
        NonPathologicalPersonalAntecedentsResponse nonPathologicalPersonalAntecedentResponse = nonPathologicalPersonalAntecedentsService
                .getNonPathologicalPersonalAntecedentsById(id);
        return ResponseEntity.ok(nonPathologicalPersonalAntecedentResponse);
    }

    @GetMapping
    public ResponseEntity<List<NonPathologicalPersonalAntecedentsResponse>> getAllNonPathologicalPersonalAntecedents() {
        List<NonPathologicalPersonalAntecedentsResponse> allNonPathologicalPersonalAntecedents = nonPathologicalPersonalAntecedentsService
                .getAllNonPathologicalPersonalAntecedents();
        return ResponseEntity.ok(allNonPathologicalPersonalAntecedents);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NonPathologicalPersonalAntecedentsResponse> updateNonPathologicalPersonalAntecedents(
            @Valid @PathVariable Long id,
            @Valid @RequestBody NonPathologicalPersonalAntecedentsRequest updateRequest) {
        NonPathologicalPersonalAntecedentsResponse updatedNonPathologicalPersonalAntecedent = nonPathologicalPersonalAntecedentsService
                .updateNonPathologicalPersonalAntecedents(id, updateRequest);
        return ResponseEntity.ok(updatedNonPathologicalPersonalAntecedent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNonPathologicalPersonalAntecedentsById(@Valid @PathVariable Long id) {
        nonPathologicalPersonalAntecedentsService.deleteNonPathologicalPersonalAntecedents(id);
        return ResponseEntity.noContent().build();
    }
}
