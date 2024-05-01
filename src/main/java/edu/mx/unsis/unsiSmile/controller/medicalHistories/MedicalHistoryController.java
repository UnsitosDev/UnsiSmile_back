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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.MedicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalHistoryResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.MedicalHistoryService;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories")
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    public MedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @PostMapping
    public ResponseEntity<MedicalHistoryResponse> createMedicalHistory(@RequestBody MedicalHistoryRequest request) {
        MedicalHistoryResponse response = medicalHistoryService.createMedicalHistory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistoryResponse> getMedicalHistoryById(@PathVariable Long id) {
        MedicalHistoryResponse response = medicalHistoryService.getMedicalHistoryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicalHistoryResponse>> getAllMedicalHistories() {
        List<MedicalHistoryResponse> response = medicalHistoryService.getAllMedicalHistories();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistoryResponse> updateMedicalHistory(@PathVariable Long id, @RequestBody MedicalHistoryRequest request) {
        MedicalHistoryResponse response = medicalHistoryService.updateMedicalHistory(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalHistory(@PathVariable Long id) {
        medicalHistoryService.deleteMedicalHistory(id);
        return ResponseEntity.noContent().build();
    }
}
