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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialFrontRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialFrontResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.FacialFrontService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/facial-fronts")
public class FacialFrontController {

    private final FacialFrontService facialFrontService;

    public FacialFrontController(FacialFrontService facialFrontService) {
        this.facialFrontService = facialFrontService;
    }

    @PostMapping
    public ResponseEntity<FacialFrontResponse> createFacialFront(@Valid @RequestBody FacialFrontRequest facialFrontRequest) {
        FacialFrontResponse createdFacialFront = facialFrontService.createFacialFront(facialFrontRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFacialFront);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacialFrontResponse> getFacialFrontById(@Valid @PathVariable Long id) {
        FacialFrontResponse facialFrontResponse = facialFrontService.getFacialFrontById(id);
        return ResponseEntity.ok(facialFrontResponse);
    }

    @GetMapping
    public ResponseEntity<List<FacialFrontResponse>> getAllFacialFronts() {
        List<FacialFrontResponse> allFacialFronts = facialFrontService.getAllFacialFronts();
        return ResponseEntity.ok(allFacialFronts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacialFrontResponse> updateFacialFront(@Valid @PathVariable Long id,
            @Valid @RequestBody FacialFrontRequest updatedFacialFrontRequest) {
        FacialFrontResponse updatedFacialFront = facialFrontService.updateFacialFront(id, updatedFacialFrontRequest);
        return ResponseEntity.ok(updatedFacialFront);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFacialFrontById(@Valid @PathVariable Long id) {
        facialFrontService.deleteFacialFrontById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byName/{facialFront}")
    public ResponseEntity<FacialFrontResponse> getFacialFrontByName(@PathVariable String facialFront) {
        FacialFrontResponse facialFrontResponse = facialFrontService.findByFacialFront(facialFront);
        return ResponseEntity.ok(facialFrontResponse);
    }
}
