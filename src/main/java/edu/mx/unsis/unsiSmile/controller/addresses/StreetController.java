package edu.mx.unsis.unsiSmile.controller.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.StreetRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.StreetResponse;
import edu.mx.unsis.unsiSmile.service.addresses.StreetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/streets")
@Validated
public class StreetController {

    private final StreetService streetService;

    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @PostMapping
    public ResponseEntity<StreetResponse> createStreet(@Valid @RequestBody StreetRequest streetRequest) {
        StreetResponse createdStreet = streetService.createStreet(streetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStreet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreetResponse> getStreetById(@PathVariable Long id) {
        StreetResponse streetResponse = streetService.getStreetById(id);
        return ResponseEntity.ok(streetResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<StreetResponse>> getStreetsByName(@PathVariable String name) {
        List<StreetResponse> streetResponses = streetService.getStreetsByName(name);
        return ResponseEntity.ok(streetResponses);
    }

    @Operation(summary = "Obtiene una lista paginada de calles de una colonia")
    @GetMapping("/neighborhood/{neighborhoodId}")
    public ResponseEntity<Page<StreetResponse>> getStreetsByNeighborhood(
            @PathVariable Long neighborhoodId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<StreetResponse> streets = streetService.getStreetsByNeighborhood(neighborhoodId, pageable);

        return ResponseEntity.ok(streets);
    }

    @Operation(summary = "Obtiene una lista paginada de todas las calles")
    @GetMapping
    public ResponseEntity<Page<StreetResponse>> getAllStreets(
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<StreetResponse> streets = streetService.getAllStreets(pageable, keyword);

        return ResponseEntity.ok(streets);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StreetResponse> updateStreet(@PathVariable Long id, @Valid @RequestBody StreetRequest updatedStreetRequest) {
        StreetResponse updatedStreet = streetService.updateStreet(id, updatedStreetRequest);
        return ResponseEntity.ok(updatedStreet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStreetById(@PathVariable Long id) {
        streetService.deleteStreetById(id);
        return ResponseEntity.noContent().build();
    }
}