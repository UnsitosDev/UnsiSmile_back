package edu.mx.unsis.unsiSmile.controller.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.NeighborhoodRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.NeighborhoodResponse;
import edu.mx.unsis.unsiSmile.service.addresses.NeighborhoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/neighborhoods")
public class NeighborhoodController {

    private final NeighborhoodService neighborhoodService;

    public NeighborhoodController(NeighborhoodService neighborhoodService) {
        this.neighborhoodService = neighborhoodService;
    }

    @PostMapping
    public ResponseEntity<NeighborhoodResponse> createNeighborhood(@Valid @RequestBody NeighborhoodRequest neighborhoodRequest) {
        NeighborhoodResponse createdNeighborhood = neighborhoodService.createNeighborhood(neighborhoodRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNeighborhood);
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

    @Operation(summary = "Obtiene las colonias de una localidad")
    @GetMapping("/locality/{localityId}")
    public ResponseEntity<List<NeighborhoodResponse>> getNeighborhoodsByLocality(@Valid @PathVariable String localityId) {
        List<NeighborhoodResponse> neighborhoodResponses = neighborhoodService.getNeighborhoodsByLocality(localityId);
        return ResponseEntity.ok(neighborhoodResponses);
    }

    @GetMapping
    public ResponseEntity<List<NeighborhoodResponse>> getAllNeighborhoods() {
        List<NeighborhoodResponse> allNeighborhoods = neighborhoodService.getAllNeighborhoods();
        return ResponseEntity.ok(allNeighborhoods);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NeighborhoodResponse> updateNeighborhood(@Valid @PathVariable Long id, @Valid @RequestBody NeighborhoodRequest updatedNeighborhoodRequest) {
        NeighborhoodResponse updatedNeighborhood = neighborhoodService.updateNeighborhood(id, updatedNeighborhoodRequest);
        return ResponseEntity.ok(updatedNeighborhood);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNeighborhoodById(@Valid @PathVariable Long id) {
        neighborhoodService.deleteNeighborhoodById(id);
        return ResponseEntity.noContent().build();
    }
}