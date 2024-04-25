package edu.mx.unsis.unsiSmile.controller.students;

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

import edu.mx.unsis.unsiSmile.dtos.request.students.CareerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.CareerResponse;
import edu.mx.unsis.unsiSmile.service.students.CareerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/careers")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @PostMapping
    public ResponseEntity<CareerResponse> createCareer(@Valid @RequestBody CareerRequest careerRequest) {
        CareerResponse createdCareer = careerService.createCareer(careerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCareer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerResponse> getCareerById(@Valid @PathVariable Long id) {
        CareerResponse careerResponse = careerService.getCareerById(id);
        return ResponseEntity.ok(careerResponse);
    }

    @GetMapping
    public ResponseEntity<List<CareerResponse>> getAllCareers() {
        List<CareerResponse> allCareers = careerService.getAllCareers();
        return ResponseEntity.ok(allCareers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerResponse> updateCareer(@Valid @PathVariable Long id,
            @Valid @RequestBody CareerRequest updatedCareerRequest) {
        CareerResponse updatedCareer = careerService.updateCareer(id, updatedCareerRequest);
        return ResponseEntity.ok(updatedCareer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCareerById(@Valid @PathVariable Long id) {

        careerService.deleteCareerById(id);
        return ResponseEntity.noContent().build();
    }
}
