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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.RegionMeasurementPocketsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.RegionMeasurementPocketsResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.RegionMeasurementPocketsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/region-measurement-pockets")
public class RegionMeasurementPocketsController {

    private final RegionMeasurementPocketsService regionMeasurementPocketsService;

    public RegionMeasurementPocketsController(RegionMeasurementPocketsService regionMeasurementPocketsService) {
        this.regionMeasurementPocketsService = regionMeasurementPocketsService;
    }

    @PostMapping
    public ResponseEntity<RegionMeasurementPocketsResponse> createRegionMeasurementPockets(
            @Valid @RequestBody RegionMeasurementPocketsRequest request) {
        RegionMeasurementPocketsResponse createdRegionMeasurementPocketsResponse = regionMeasurementPocketsService.createRegionMeasurementPockets(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRegionMeasurementPocketsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionMeasurementPocketsResponse> getRegionMeasurementPocketsById(@Valid @PathVariable Long id) {
        RegionMeasurementPocketsResponse regionMeasurementPocketsResponse = regionMeasurementPocketsService.getRegionMeasurementPocketsById(id);
        return ResponseEntity.ok(regionMeasurementPocketsResponse);
    }

    @GetMapping
    public ResponseEntity<List<RegionMeasurementPocketsResponse>> getAllRegionMeasurementPockets() {
        List<RegionMeasurementPocketsResponse> allRegionMeasurementPockets = regionMeasurementPocketsService.getAllRegionMeasurementPockets();
        return ResponseEntity.ok(allRegionMeasurementPockets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionMeasurementPocketsResponse> updateRegionMeasurementPockets(@Valid @PathVariable Long id,
                                                                        @Valid @RequestBody RegionMeasurementPocketsRequest updateRequest) {
        RegionMeasurementPocketsResponse updatedRegionMeasurementPocketsResponse = regionMeasurementPocketsService.updateRegionMeasurementPockets(id, updateRequest);
        return ResponseEntity.ok(updatedRegionMeasurementPocketsResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegionMeasurementPockets(@Valid @PathVariable Long id) {
        regionMeasurementPocketsService.deleteRegionMeasurementPockets(id);
        return ResponseEntity.noContent().build();
    }
}
