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

import edu.mx.unsis.unsiSmile.dtos.request.CareerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CareerResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.service.CareerService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/unsismile/api/v1/careers")
@Slf4j
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @PostMapping
    public ResponseEntity<CareerResponse> createCareer(@RequestBody CareerRequest careerRequest) {
        try {
            CareerResponse createdCareer = careerService.createCareer(careerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCareer);
        } catch (AppException ex) {
            log.error("Failed to create career: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerResponse> getCareerById(@NotNull @PathVariable Long id) {

        try {
            CareerResponse careerResponse = careerService.getCareerById(id);
            return ResponseEntity.ok(careerResponse);
        } catch (AppException ex) {
            log.error("Failed to fetch career: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CareerResponse>> getAllCareers() {
        try {
            List<CareerResponse> allCareers = careerService.getAllCareers();
            return ResponseEntity.ok(allCareers);
        } catch (AppException ex) {
            log.error("Failed to fetch careers: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerResponse> updateCareer(@PathVariable Long id, @RequestBody CareerRequest updatedCareerRequest) {
        try {
            CareerResponse updatedCareer = careerService.updateCareer(id, updatedCareerRequest);
            return ResponseEntity.ok(updatedCareer);
        } catch (AppException ex) {
            log.error("Failed to update career: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCareerById(@PathVariable Long id) {
        try {
            careerService.deleteCareerById(id);
            return ResponseEntity.noContent().build();
        } catch (AppException ex) {
            log.error("Failed to delete career: {}", ex.getMessage());
            return ResponseEntity.status(ex.getHttpStatus()).build();
        }
    }
}
