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

    @Operation(summary = "Obtiene una lista paginada de colonias de una localidad")
    @GetMapping("/locality/{localityId}")
    public ResponseEntity<Page<NeighborhoodResponse>> getNeighborhoodsByLocality(
            @PathVariable Long localityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<NeighborhoodResponse> neighborhoods = neighborhoodService.getNeighborhoodsByLocalityId(localityId, pageable);

        return ResponseEntity.ok(neighborhoods);
    }

    @Operation(summary = "Obtener una lista paginada de colonias.")
    @GetMapping
    public ResponseEntity<Page<NeighborhoodResponse>> getAllNeighborhoods(
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<NeighborhoodResponse> neighborhoods = neighborhoodService.getAllNeighborhoods(pageable, keyword);

        return ResponseEntity.ok(neighborhoods);
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