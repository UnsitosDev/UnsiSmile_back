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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HereditaryFamilyHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HereditaryFamilyHistoryResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.HereditaryFamilyHistoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/hereditary-family-histories")
public class HereditaryFamilyHistoryController {

    private final HereditaryFamilyHistoryService familyHistoryService;

    public HereditaryFamilyHistoryController(HereditaryFamilyHistoryService familyHistoryService) {
        this.familyHistoryService = familyHistoryService;
    }

    @PostMapping
    public ResponseEntity<HereditaryFamilyHistoryResponse> createFamilyHistory(@Valid @RequestBody HereditaryFamilyHistoryRequest familyHistoryRequest) {
        HereditaryFamilyHistoryResponse createdFamilyHistory = familyHistoryService.createFamilyHistory(familyHistoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFamilyHistory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HereditaryFamilyHistoryResponse> getFamilyHistoryById(@Valid @PathVariable Long id) {
        HereditaryFamilyHistoryResponse familyHistoryResponse = familyHistoryService.getFamilyHistoryById(id);
        return ResponseEntity.ok(familyHistoryResponse);
    }

    @GetMapping
    public ResponseEntity<List<HereditaryFamilyHistoryResponse>> getAllFamilyHistories() {
        List<HereditaryFamilyHistoryResponse> allFamilyHistories = familyHistoryService.getAllFamilyHistories();
        return ResponseEntity.ok(allFamilyHistories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HereditaryFamilyHistoryResponse> updateFamilyHistory(@Valid @PathVariable Long id,
            @Valid @RequestBody HereditaryFamilyHistoryRequest updatedFamilyHistoryRequest) {
        HereditaryFamilyHistoryResponse updatedFamilyHistory = familyHistoryService.updateFamilyHistory(id, updatedFamilyHistoryRequest);
        return ResponseEntity.ok(updatedFamilyHistory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFamilyHistoryById(@Valid @PathVariable Long id) {
        familyHistoryService.deleteFamilyHistoryById(id);
        return ResponseEntity.noContent().build();
    }
}
