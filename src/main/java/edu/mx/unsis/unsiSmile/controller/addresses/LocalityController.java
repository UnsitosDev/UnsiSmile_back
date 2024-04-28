package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.LocalityResponse;
import edu.mx.unsis.unsiSmile.model.MunicipalityModel;
import edu.mx.unsis.unsiSmile.service.addresses.LocalityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/addresses/locality")
public class LocalityController {

    private final LocalityService localityService;

    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalityResponse> getLocalityById(@Valid @PathVariable String id) {
        LocalityResponse localityResponse = localityService.getLocalityById(id);
        return ResponseEntity.ok(localityResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<LocalityResponse>> getLocalitiesByName(@Valid @PathVariable String name) {
        List<LocalityResponse> localityResponses = localityService.getLocalitiesByName(name);
        return ResponseEntity.ok(localityResponses);
    }

    @GetMapping("/postal-code/{postalCode}")
    public ResponseEntity<List<LocalityResponse>> getLocalitiesByPostalCode(@Valid @PathVariable String postalCode) {
        List<LocalityResponse> localityResponses = localityService.getLocalitiesByPostalCode(postalCode);
        return ResponseEntity.ok(localityResponses);
    }

    @GetMapping("/municipality/{municipalityId}")
    public ResponseEntity<List<LocalityResponse>> getLocalitiesByMunicipality(@Valid @PathVariable String municipalityId) {
        // Assuming you have a method to get the MunicipalityModel by ID
        MunicipalityModel municipality = getMunicipalityById(municipalityId);
        List<LocalityResponse> localityResponses = localityService.getLocalitiesByMunicipality(municipality);
        return ResponseEntity.ok(localityResponses);
    }

    @GetMapping
    public ResponseEntity<List<LocalityResponse>> getAllLocalities() {
        List<LocalityResponse> allLocalities = localityService.getAllLocalities();
        return ResponseEntity.ok(allLocalities);
    }

    private MunicipalityModel getMunicipalityById(String municipalityId) {
        // Implement the logic to fetch the MunicipalityModel by ID
        // This is just a placeholder, you need to provide the actual implementation
        return new MunicipalityModel();
    }
}