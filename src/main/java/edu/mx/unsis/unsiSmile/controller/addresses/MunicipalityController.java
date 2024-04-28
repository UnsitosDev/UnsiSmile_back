package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.MunicipalityResponse;
import edu.mx.unsis.unsiSmile.model.StateModel;
import edu.mx.unsis.unsiSmile.service.addresses.MunicipalityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/addresses/municipality")
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipalityResponse> getMunicipalityById(@Valid @PathVariable String id) {
        MunicipalityResponse municipalityResponse = municipalityService.getMunicipalityById(id);
        return ResponseEntity.ok(municipalityResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<MunicipalityResponse>> getMunicipalitiesByName(@Valid @PathVariable String name) {
        List<MunicipalityResponse> municipalityResponses = municipalityService.getMunicipalitiesByName(name);
        return ResponseEntity.ok(municipalityResponses);
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<MunicipalityResponse>> getMunicipalitiesByState(@Valid @PathVariable String stateId) {
        // Assuming you have a method to get the StateModel by ID
        StateModel state = getStateById(stateId);
        List<MunicipalityResponse> municipalityResponses = municipalityService.getMunicipalitiesByState(state);
        return ResponseEntity.ok(municipalityResponses);
    }

    @GetMapping
    public ResponseEntity<List<MunicipalityResponse>> getAllMunicipalities() {
        List<MunicipalityResponse> allMunicipalities = municipalityService.getAllMunicipalities();
        return ResponseEntity.ok(allMunicipalities);
    }

    private StateModel getStateById(String stateId) {
        // Implement the logic to fetch the StateModel by ID
        // This is just a placeholder, you need to provide the actual implementation
        return new StateModel();
    }
}