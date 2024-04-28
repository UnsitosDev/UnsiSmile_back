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

import edu.mx.unsis.unsiSmile.dtos.request.patients.NationalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.NationalityResponse;
import edu.mx.unsis.unsiSmile.service.patients.NationalityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/patients/nationality")
public class NationalityController {

    private final NationalityService nationalityService;

    public NationalityController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    @PostMapping
    public ResponseEntity<NationalityResponse> createNationality(@Valid @RequestBody NationalityRequest nationalityRequest) {
        NationalityResponse createdNationality = nationalityService.createNationality(nationalityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNationality);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NationalityResponse> getNationalityById(@Valid @PathVariable Long id) {
        NationalityResponse nationalityResponse = nationalityService.getNationalityById(id);
        return ResponseEntity.ok(nationalityResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<NationalityResponse> getNationalityByName(@Valid @PathVariable String name) {
        NationalityResponse nationalityResponse = nationalityService.getNationalityByName(name);
        return ResponseEntity.ok(nationalityResponse);
    }

    @GetMapping
    public ResponseEntity<List<NationalityResponse>> getAllNationalities() {
        List<NationalityResponse> allNationalities = nationalityService.getAllNationalities();
        return ResponseEntity.ok(allNationalities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NationalityResponse> updateNationality(@Valid @PathVariable Long id, @Valid @RequestBody NationalityRequest updatedNationalityRequest) {
        NationalityResponse updatedNationality = nationalityService.updateNationality(id, updatedNationalityRequest);
        return ResponseEntity.ok(updatedNationality);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNationalityById(@Valid @PathVariable Long id) {
        nationalityService.deleteNationalityById(id);
        return ResponseEntity.noContent().build();
    }
}