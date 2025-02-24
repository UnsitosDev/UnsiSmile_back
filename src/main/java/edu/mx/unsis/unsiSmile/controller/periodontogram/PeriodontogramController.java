package edu.mx.unsis.unsiSmile.controller.periodontogram;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.periodontogram.PeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.periodontograms.PeriodontogramResponse;
import edu.mx.unsis.unsiSmile.service.periodontogram.PeriodontogramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("unsismile/api/v1/medical-records/periodontograms")
@AllArgsConstructor
@Tag(name = "Periodontogram Controller", description = "Endpoints for managing periodontograms")
public class PeriodontogramController {

    private final PeriodontogramService periodontogramService;

    @GetMapping
    @Operation(summary = "Get all periodontograms", description = "Retrieve a list of all periodontograms")
    public List<PeriodontogramResponse> getAllPeriodontograms() {
        return periodontogramService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get periodontogram by ID", description = "Retrieve a periodontogram by its ID")
    public ResponseEntity<PeriodontogramResponse> getPeriodontogramById(@PathVariable Long id) {
        PeriodontogramResponse periodontogram = periodontogramService.findById(id);
        return ResponseEntity.ok(periodontogram);
    }

    @GetMapping("/patient/{patientId}/form-section/{formSectionId}")
    @Operation(summary = "Get periodontogram by patient ID and form section ID", description = "Retrieve a periodontogram by patient ID and form section ID")
    public ResponseEntity<PeriodontogramResponse> getPeriodontogramByPatientIdAndFormSectionId(
            @PathVariable Long patientId, @PathVariable Long formSectionId) {
        PeriodontogramResponse periodontogram = periodontogramService.findByPatientIdAndFormSectionId(patientId,
                formSectionId);
        return ResponseEntity.ok(periodontogram);
    }

    @GetMapping("/patient/{patientId}/current")
    @Operation(summary = "Get most recent periodontogram by patient ID", description = "Retrieve the most recent periodontogram by patient ID")
    public ResponseEntity<PeriodontogramResponse> getMostRecentPeriodontogramByPatientId(@PathVariable Long patientId) {
        PeriodontogramResponse periodontogram = periodontogramService.findMostRecentByPatientId(patientId);
        return ResponseEntity.ok(periodontogram);
    }

    @PostMapping
    @Operation(summary = "Create a new periodontogram", description = "Create a new periodontogram")
    public PeriodontogramResponse createPeriodontogram(@RequestBody PeriodontogramRequest periodontogramRequest) {
        return periodontogramService.save(periodontogramRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a periodontogram", description = "Update an existing periodontogram by its ID")
    public ResponseEntity<PeriodontogramResponse> updatePeriodontogram(@PathVariable Long id,
            @RequestBody PeriodontogramRequest periodontogramRequest) {
        PeriodontogramResponse updatedPeriodontogram = periodontogramService.update(id, periodontogramRequest);
        return ResponseEntity.ok(updatedPeriodontogram);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a periodontogram", description = "Delete a periodontogram by its ID")
    public ResponseEntity<Void> deletePeriodontogram(@PathVariable Long id) {
        periodontogramService.delete(id);
        return ResponseEntity.noContent().build();
    }
}