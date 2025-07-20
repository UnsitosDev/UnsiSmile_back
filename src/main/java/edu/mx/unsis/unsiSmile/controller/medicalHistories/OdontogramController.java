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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramSimpleResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.OdontogramService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/odontograms")
public class OdontogramController {

    private final OdontogramService odontogramService;

    public OdontogramController(OdontogramService odontogramService) {
        this.odontogramService = odontogramService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontogramResponse> getOdontogramById(@Valid @PathVariable Long id) {
        OdontogramResponse odontogramResponse = odontogramService.getOdontogramById(id);
        return ResponseEntity.ok(odontogramResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OdontogramResponse> updateOdontogram(@Valid @PathVariable Long id,
            @Valid @RequestBody OdontogramRequest updateRequest) {
        OdontogramResponse updatedOdontogramResponse = odontogramService.updateOdontogram(id, updateRequest);
        return ResponseEntity.ok(updatedOdontogramResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOdontogram(@Valid @PathVariable Long id) {
        odontogramService.deleteOdontogram(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createOdontogram(@Valid @RequestBody OdontogramRequest odontogramDTO) {
        odontogramService.saveOdontogram(odontogramDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/latest/{patientClinicalHistoryId}")
    public OdontogramResponse getOdontogramDetails(@PathVariable Long patientClinicalHistoryId) {
        return odontogramService.getOdontogramDetails(patientClinicalHistoryId);
    }       

    @GetMapping("/patient-clinical-history/{patientClinicalHistoryId}")
    public ResponseEntity<List<OdontogramSimpleResponse>> getOdontogramsByPatientMedicalRecordId(@PathVariable Long patientClinicalHistoryId) {
        List<OdontogramSimpleResponse> odontograms = odontogramService.getOdontogramsByPatientMedicalRecordId(patientClinicalHistoryId);
        return ResponseEntity.ok(odontograms);
    }

}
