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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialProfileRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialProfileResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.FacialProfileService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/facial-profiles")
public class FacialProfileController {

    private final FacialProfileService facialProfileService;

    public FacialProfileController(FacialProfileService facialProfileService) {
        this.facialProfileService = facialProfileService;
    }

    @PostMapping
    public ResponseEntity<FacialProfileResponse> createFacialProfile(@Valid @RequestBody FacialProfileRequest facialProfileRequest) {
        FacialProfileResponse createdFacialProfile = facialProfileService.createFacialProfile(facialProfileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFacialProfile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacialProfileResponse> getFacialProfileById(@PathVariable Long id) {
        FacialProfileResponse facialProfileResponse = facialProfileService.getFacialProfileById(id);
        return ResponseEntity.ok(facialProfileResponse);
    }

    @GetMapping
    public ResponseEntity<List<FacialProfileResponse>> getAllFacialProfiles() {
        List<FacialProfileResponse> allFacialProfiles = facialProfileService.getAllFacialProfiles();
        return ResponseEntity.ok(allFacialProfiles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacialProfileResponse> updateFacialProfile(@Valid @PathVariable Long id,
                                                                     @RequestBody FacialProfileRequest updatedFacialProfileRequest) {
        FacialProfileResponse updatedFacialProfile = facialProfileService.updateFacialProfile(id, updatedFacialProfileRequest);
        return ResponseEntity.ok(updatedFacialProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFacialProfile(@PathVariable Long id) {
        facialProfileService.deleteFacialProfile(id);
        return ResponseEntity.noContent().build();
    }
}
