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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRegionPeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothRegionPeriodontogramResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ToothRegionPeriodontogramService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/tooth-region-periodontograms")
public class ToothRegionPeriodontogramController {

    private final ToothRegionPeriodontogramService toothRegionPeriodontogramService;

    public ToothRegionPeriodontogramController(ToothRegionPeriodontogramService toothRegionPeriodontogramService) {
        this.toothRegionPeriodontogramService = toothRegionPeriodontogramService;
    }

    @PostMapping
    public ResponseEntity<ToothRegionPeriodontogramResponse> createToothRegionPeriodontogram(
            @Valid @RequestBody ToothRegionPeriodontogramRequest request) {
        ToothRegionPeriodontogramResponse createdToothRegionPeriodontogramResponse = toothRegionPeriodontogramService
                .createToothRegionPeriodontogram(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToothRegionPeriodontogramResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToothRegionPeriodontogramResponse> getToothRegionPeriodontogramById(
            @Valid @PathVariable Long id) {
        ToothRegionPeriodontogramResponse toothRegionPeriodontogramResponse = toothRegionPeriodontogramService
                .getToothRegionPeriodontogramById(id);
        return ResponseEntity.ok(toothRegionPeriodontogramResponse);
    }

    @GetMapping
    public ResponseEntity<List<ToothRegionPeriodontogramResponse>> getAllToothRegionPeriodontograms() {
        List<ToothRegionPeriodontogramResponse> allToothRegionPeriodontograms = toothRegionPeriodontogramService
                .getAllToothRegionPeriodontograms();
        return ResponseEntity.ok(allToothRegionPeriodontograms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToothRegionPeriodontogramResponse> updateToothRegionPeriodontogram(
            @Valid @PathVariable Long id,
            @Valid @RequestBody ToothRegionPeriodontogramRequest updateRequest) {
        ToothRegionPeriodontogramResponse updatedToothRegionPeriodontogramResponse = toothRegionPeriodontogramService
                .updateToothRegionPeriodontogram(id, updateRequest);
        return ResponseEntity.ok(updatedToothRegionPeriodontogramResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToothRegionPeriodontogram(@Valid @PathVariable Long id) {
        toothRegionPeriodontogramService.deleteToothRegionPeriodontogram(id);
        return ResponseEntity.noContent().build();
    }
}
