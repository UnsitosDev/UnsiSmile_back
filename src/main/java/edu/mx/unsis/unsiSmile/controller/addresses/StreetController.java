package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.StreetResponse;
import edu.mx.unsis.unsiSmile.model.NeighborhoodModel;
import edu.mx.unsis.unsiSmile.service.addresses.StreetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/addresses/street")
public class StreetController {

    private final StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreetResponse> getStreetById(@Valid @PathVariable Long id) {
        StreetResponse streetResponse = streetService.getStreetById(id);
        return ResponseEntity.ok(streetResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<StreetResponse>> getStreetsByName(@Valid @PathVariable String name) {
        List<StreetResponse> streetResponses = streetService.getStreetsByName(name);
        return ResponseEntity.ok(streetResponses);
    }

    @GetMapping("/neighborhood/{neighborhoodId}")
    public ResponseEntity<List<StreetResponse>> getStreetsByNeighborhood(@Valid @PathVariable String neighborhoodId) {
        // Assuming you have a method to get the NeighborhoodModel by ID
        NeighborhoodModel neighborhood = getNeighborhoodById(neighborhoodId);
        List<StreetResponse> streetResponses = streetService.getStreetsByNeighborhood(neighborhood);
        return ResponseEntity.ok(streetResponses);
    }

    @GetMapping
    public ResponseEntity<List<StreetResponse>> getAllStreets() {
        List<StreetResponse> allStreets = streetService.getAllStreets();
        return ResponseEntity.ok(allStreets);
    }

    private NeighborhoodModel getNeighborhoodById(String neighborhoodId) {
        // Implement the logic to fetch the NeighborhoodModel by ID
        // This is just a placeholder, you need to provide the actual implementation
        return new NeighborhoodModel();
    }
}