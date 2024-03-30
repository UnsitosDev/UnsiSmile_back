package edu.mx.unsis.unsiSmile.controller;

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

import edu.mx.unsis.unsiSmile.dtos.request.GenderRequest;
import edu.mx.unsis.unsiSmile.dtos.response.GenderResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.service.GenderService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/unsismile/api/v1/genders")
@Slf4j
public class GenderController {

    private final GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @PostMapping
    public ResponseEntity<GenderResponse> createGender(@RequestBody GenderRequest genderRequest) {
        try {
            GenderResponse createdGender = genderService.createGender(genderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGender);
        } catch (AppException ex) {
            log.error("Failed to create gender: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenderResponse> getGenderById(@PathVariable Long id) {

        try {
            GenderResponse genderResponse = genderService.getGenderById(id);
            return ResponseEntity.ok(genderResponse);
        } catch (AppException ex) {
            log.error("Failed to fetch gender: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GenderResponse>> getAllGenders() {
        try {
            List<GenderResponse> allGenders = genderService.getAllGenders();
            return ResponseEntity.ok(allGenders);
        } catch (AppException ex) {
            log.error("Failed to fetch genders: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenderResponse> updateGender(@PathVariable Long id, @RequestBody GenderRequest updatedGenderRequest) {
        try {
            GenderResponse updatedGender = genderService.updateGender(id, updatedGenderRequest);
            return ResponseEntity.ok(updatedGender);
        } catch (AppException ex) {
            log.error("Failed to update gender: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenderById(@PathVariable Long id) {
        try {
            genderService.deleteGenderById(id);
            return ResponseEntity.noContent().build();
        } catch (AppException ex) {
            log.error("Failed to delete gender: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }
}
