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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothDetailResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ToothDetailService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/tooth-details")
public class ToothDetailController {

    private final ToothDetailService toothDetailService;

    public ToothDetailController(ToothDetailService toothDetailService) {
        this.toothDetailService = toothDetailService;
    }

    @PostMapping
    public ResponseEntity<ToothDetailResponse> createToothDetail(@Valid @RequestBody ToothDetailRequest request) {
        ToothDetailResponse createdToothDetailResponse = toothDetailService.createToothDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToothDetailResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToothDetailResponse> getToothDetailById(@Valid @PathVariable Long id) {
        ToothDetailResponse toothDetailResponse = toothDetailService.getToothDetailById(id);
        return ResponseEntity.ok(toothDetailResponse);
    }

    @GetMapping
    public ResponseEntity<List<ToothDetailResponse>> getAllToothDetails() {
        List<ToothDetailResponse> allToothDetails = toothDetailService.getAllToothDetails();
        return ResponseEntity.ok(allToothDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToothDetailResponse> updateToothDetail(@Valid @PathVariable Long id,
                                                                        @Valid @RequestBody ToothDetailRequest updateRequest) {
        ToothDetailResponse updatedToothDetailResponse = toothDetailService.updateToothDetail(id, updateRequest);
        return ResponseEntity.ok(updatedToothDetailResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToothDetail(@Valid @PathVariable Long id) {
        toothDetailService.deleteToothDetail(id);
        return ResponseEntity.noContent().build();
    }
}
