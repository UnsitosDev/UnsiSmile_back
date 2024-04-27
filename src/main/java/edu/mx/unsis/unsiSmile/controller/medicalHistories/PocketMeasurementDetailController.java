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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.PocketMeasurementDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PocketMeasurementDetailResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PocketMeasurementDetailService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/pocket-measurement-details")
public class PocketMeasurementDetailController {

    private final PocketMeasurementDetailService pocketMeasurementDetailService;

    public PocketMeasurementDetailController(PocketMeasurementDetailService pocketMeasurementDetailService) {
        this.pocketMeasurementDetailService = pocketMeasurementDetailService;
    }

    @PostMapping
    public ResponseEntity<PocketMeasurementDetailResponse> createPocketMeasurementDetail(
            @Valid @RequestBody PocketMeasurementDetailRequest request) {
        PocketMeasurementDetailResponse createdPocketMeasurementDetailResponse = pocketMeasurementDetailService.createPocketMeasurementDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPocketMeasurementDetailResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PocketMeasurementDetailResponse> getPocketMeasurementDetailById(@Valid @PathVariable Long id) {
        PocketMeasurementDetailResponse pocketMeasurementDetailResponse = pocketMeasurementDetailService.getPocketMeasurementDetailById(id);
        return ResponseEntity.ok(pocketMeasurementDetailResponse);
    }

    @GetMapping
    public ResponseEntity<List<PocketMeasurementDetailResponse>> getAllPocketMeasurementDetails() {
        List<PocketMeasurementDetailResponse> allPocketMeasurementDetails = pocketMeasurementDetailService.getAllPocketMeasurementDetails();
        return ResponseEntity.ok(allPocketMeasurementDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PocketMeasurementDetailResponse> updatePocketMeasurementDetail(@Valid @PathVariable Long id,
                                                                        @Valid @RequestBody PocketMeasurementDetailRequest updateRequest) {
        PocketMeasurementDetailResponse updatedPocketMeasurementDetailResponse = pocketMeasurementDetailService.updatePocketMeasurementDetail(id, updateRequest);
        return ResponseEntity.ok(updatedPocketMeasurementDetailResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePocketMeasurementDetail(@Valid @PathVariable Long id) {
        pocketMeasurementDetailService.deletePocketMeasurementDetail(id);
        return ResponseEntity.noContent().build();
    }
}
