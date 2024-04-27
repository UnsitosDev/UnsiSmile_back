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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.PeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PeriodontogramResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PeriodontogramService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/periodontograms")
public class PeriodontogramController {

    private final PeriodontogramService periodontogramService;

    public PeriodontogramController(PeriodontogramService periodontogramService) {
        this.periodontogramService = periodontogramService;
    }

    @PostMapping
    public ResponseEntity<PeriodontogramResponse> createPeriodontogram(@Valid @RequestBody PeriodontogramRequest request) {
        PeriodontogramResponse createdPeriodontogramResponse = periodontogramService.createPeriodontogram(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPeriodontogramResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodontogramResponse> getPeriodontogramById(@Valid @PathVariable Long id) {
        PeriodontogramResponse periodontogramResponse = periodontogramService.getPeriodontogramById(id);
        return ResponseEntity.ok(periodontogramResponse);
    }

    @GetMapping
    public ResponseEntity<List<PeriodontogramResponse>> getAllPeriodontograms() {
        List<PeriodontogramResponse> allPeriodontograms = periodontogramService.getAllPeriodontograms();
        return ResponseEntity.ok(allPeriodontograms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodontogramResponse> updatePeriodontogram(@Valid @PathVariable Long id,
                                                                        @Valid @RequestBody PeriodontogramRequest updateRequest) {
        PeriodontogramResponse updatedPeriodontogramResponse = periodontogramService.updatePeriodontogram(id, updateRequest);
        return ResponseEntity.ok(updatedPeriodontogramResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeriodontogram(@Valid @PathVariable Long id) {
        periodontogramService.deletePeriodontogram(id);
        return ResponseEntity.noContent().build();
    }
}
