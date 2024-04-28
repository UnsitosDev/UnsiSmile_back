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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.VitalSignsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.VitalSignsResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.VitalSignsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/vital-signs")
public class VitalSignsController {

    private final VitalSignsService vitalSignsService;

    public VitalSignsController(VitalSignsService vitalSignsService) {
        this.vitalSignsService = vitalSignsService;
    }

    @PostMapping
    public ResponseEntity<VitalSignsResponse> createVitalSigns(@Valid @RequestBody VitalSignsRequest vitalSignsRequest) {
        VitalSignsResponse createdVitalSigns = vitalSignsService.createVitalSigns(vitalSignsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVitalSigns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VitalSignsResponse> getVitalSignsById(@Valid @PathVariable Long id) {
        VitalSignsResponse vitalSignsResponse = vitalSignsService.getVitalSignsById(id);
        return ResponseEntity.ok(vitalSignsResponse);
    }

    @GetMapping
    public ResponseEntity<List<VitalSignsResponse>> getAllVitalSigns() {
        List<VitalSignsResponse> allVitalSigns = vitalSignsService.getAllVitalSigns();
        return ResponseEntity.ok(allVitalSigns);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VitalSignsResponse> updateVitalSigns(@Valid @PathVariable Long id, @Valid @RequestBody VitalSignsRequest updatedVitalSignsRequest) {
        VitalSignsResponse updatedVitalSigns = vitalSignsService.updateVitalSigns(id, updatedVitalSignsRequest);
        return ResponseEntity.ok(updatedVitalSigns);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVitalSignsById(@Valid @PathVariable Long id) {
        vitalSignsService.deleteVitalSignsById(id);
        return ResponseEntity.noContent().build();
    }
}