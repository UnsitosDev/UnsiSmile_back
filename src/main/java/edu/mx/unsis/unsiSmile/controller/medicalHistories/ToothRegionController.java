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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRegionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothRegionResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ToothRegionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/tooth-regions")
public class ToothRegionController {

    private final ToothRegionService toothRegionService;

    public ToothRegionController(ToothRegionService toothRegionService) {
        this.toothRegionService = toothRegionService;
    }

    @PostMapping
    public ResponseEntity<ToothRegionResponse> createToothRegion(@Valid @RequestBody ToothRegionRequest request) {
        ToothRegionResponse createdToothRegionResponse = toothRegionService.createToothRegion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToothRegionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToothRegionResponse> getToothRegionById(@Valid @PathVariable Long id) {
        ToothRegionResponse toothRegionResponse = toothRegionService.getToothRegionById(id);
        return ResponseEntity.ok(toothRegionResponse);
    }

    @GetMapping
    public ResponseEntity<List<ToothRegionResponse>> getAllToothRegions() {
        List<ToothRegionResponse> allToothRegions = toothRegionService.getAllToothRegions();
        return ResponseEntity.ok(allToothRegions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToothRegionResponse> updateToothRegion(@Valid @PathVariable Long id,
                                                                        @Valid @RequestBody ToothRegionRequest updateRequest) {
        ToothRegionResponse updatedToothRegionResponse = toothRegionService.updateToothRegion(id, updateRequest);
        return ResponseEntity.ok(updatedToothRegionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToothRegion(@Valid @PathVariable Long id) {
        toothRegionService.deleteToothRegionById(id);
        return ResponseEntity.noContent().build();
    }
}
