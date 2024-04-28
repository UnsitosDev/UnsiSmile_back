package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.NeighborhoodResponse;
import edu.mx.unsis.unsiSmile.model.LocalityModel;
import edu.mx.unsis.unsiSmile.service.addresses.NeighborhoodService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/addresses/neighborhoods")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    public NeighborhoodController(NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NeighborhoodResponse> getNeighborhoodById(@Valid @PathVariable Long id) {
        NeighborhoodResponse neighborhoodResponse = neighborhoodService.getNeighborhoodById(id);
        return ResponseEntity.ok(neighborhoodResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<NeighborhoodResponse>> getNeighborhoodsByName(@Valid @PathVariable String name) {
        List<NeighborhoodResponse> neighborhoodResponses = neighborhoodService.getNeighborhoodsByName(name);
        return ResponseEntity.ok(neighborhoodResponses);
    }

    @GetMapping("/locality/{localityId}")
    public ResponseEntity<List<NeighborhoodResponse>> getNeighborhoodsByLocality(@Valid @PathVariable String localityId) {
        // Assuming you have a method to get the LocalityModel by ID
        LocalityModel locality = getLocalityById(localityId);
        List<NeighborhoodResponse> neighborhoodResponses = neighborhoodService.getNeighborhoodsByLocality(locality);
        return ResponseEntity.ok(neighborhoodResponses);
    }

    @GetMapping
    public ResponseEntity<List<NeighborhoodResponse>> getAllNeighborhoods() {
        List<NeighborhoodResponse> allNeighborhoods = neighborhoodService.getAllNeighborhoods();
        return ResponseEntity.ok(allNeighborhoods);
    }

    private LocalityModel getLocalityById(String localityId) {
        // Implement the logic to fetch the LocalityModel by ID
        // This is just a placeholder, you need to provide the actual implementation
        return new LocalityModel();
    }
}