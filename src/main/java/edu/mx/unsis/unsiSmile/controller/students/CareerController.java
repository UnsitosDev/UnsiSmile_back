package edu.mx.unsis.unsiSmile.controller.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.CareerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.CareerResponse;
import edu.mx.unsis.unsiSmile.service.students.CareerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CareerResponse> getCareerById(@Valid @PathVariable String id) {
        CareerResponse careerResponse = careerService.getCareerById(id);
        return ResponseEntity.ok(careerResponse);
    }

    @GetMapping
    public ResponseEntity<List<CareerResponse>> getAllCareers() {
        List<CareerResponse> allCareers = careerService.getAllCareers();
        return ResponseEntity.ok(allCareers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareerResponse> updateCareer(@Valid @PathVariable String id,
            @Valid @RequestBody CareerRequest updatedCareerRequest) {
        CareerResponse updatedCareer = careerService.updateCareer(id, updatedCareerRequest);
        return ResponseEntity.ok(updatedCareer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCareerById(@Valid @PathVariable String id) {

        careerService.deleteCareerById(id);
        return ResponseEntity.noContent().build();
    }
}