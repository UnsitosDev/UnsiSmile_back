package edu.mx.unsis.unsiSmile.controller.patients;

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

import edu.mx.unsis.unsiSmile.dtos.request.patients.MaritalStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.MaritalStatusResponse;
import edu.mx.unsis.unsiSmile.service.patients.MaritalStatusService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/patients/marital-status")
public class MaritalStatusController {

    private final MaritalStatusService maritalStatusService;

    public MaritalStatusController(MaritalStatusService maritalStatusService) {
        this.maritalStatusService = maritalStatusService;
    }

    @PostMapping
    public ResponseEntity<MaritalStatusResponse> createMaritalStatus(@Valid @RequestBody MaritalStatusRequest maritalStatusRequest) {
        MaritalStatusResponse createdMaritalStatus = maritalStatusService.createMaritalStatus(maritalStatusRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMaritalStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaritalStatusResponse> getMaritalStatusById(@Valid @PathVariable Long id) {
        MaritalStatusResponse maritalStatusResponse = maritalStatusService.getMaritalStatusById(id);
        return ResponseEntity.ok(maritalStatusResponse);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<MaritalStatusResponse> getMaritalStatusByStatus(@Valid @PathVariable String status) {
        MaritalStatusResponse maritalStatusResponse = maritalStatusService.getMaritalStatusByStatus(status);
        return ResponseEntity.ok(maritalStatusResponse);
    }

    @GetMapping
    public ResponseEntity<List<MaritalStatusResponse>> getAllMaritalStatus() {
        List<MaritalStatusResponse> allMaritalStatus = maritalStatusService.getAllMaritalStatus();
        return ResponseEntity.ok(allMaritalStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaritalStatusResponse> updateMaritalStatus(@Valid @PathVariable Long id, @Valid @RequestBody MaritalStatusRequest updatedMaritalStatusRequest) {
        MaritalStatusResponse updatedMaritalStatus = maritalStatusService.updateMaritalStatus(id, updatedMaritalStatusRequest);
        return ResponseEntity.ok(updatedMaritalStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaritalStatusById(@Valid @PathVariable Long id) {
        maritalStatusService.deleteMaritalStatusById(id);
        return ResponseEntity.noContent().build();
    }
}
