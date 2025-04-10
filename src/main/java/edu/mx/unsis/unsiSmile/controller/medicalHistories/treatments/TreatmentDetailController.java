package edu.mx.unsis.unsiSmile.controller.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.treatments.TreatmentDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TreatmentDetail")
@RestController
@RequestMapping("/unsismile/api/v1/treatment-details")
@RequiredArgsConstructor
public class TreatmentDetailController {

    private final TreatmentDetailService treatmentDetailService;

    @PostMapping
    public ResponseEntity<TreatmentDetailResponse> createTreatmentDetail(@Valid @RequestBody TreatmentDetailRequest request) {
        TreatmentDetailResponse response = treatmentDetailService.createTreatmentDetail(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
