package edu.mx.unsis.unsiSmile.controller.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.LocalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.LocalityResponse;
import edu.mx.unsis.unsiSmile.service.addresses.LocalityService;
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
@RequestMapping("/unsismile/api/v1/locality")
@Validated
public class LocalityController {

    private final LocalityService localityService;

    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    @PostMapping
    public ResponseEntity<LocalityResponse> createLocality(@Valid @RequestBody LocalityRequest localityRequest) {
        LocalityResponse createdLocality = localityService.createLocality(localityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocality);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalityResponse> getLocalityById(@PathVariable Long id) {
        LocalityResponse localityResponse = localityService.getLocalityById(id);
        return ResponseEntity.ok(localityResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<LocalityResponse>> getLocalitiesByName(@PathVariable String name) {
        List<LocalityResponse> localityResponses = localityService.getLocalitiesByName(name);
        return ResponseEntity.ok(localityResponses);
    }

    @GetMapping("/postal-code/{postalCode}")
    public ResponseEntity<List<LocalityResponse>> getLocalitiesByPostalCode(@PathVariable String postalCode) {
        List<LocalityResponse> localityResponses = localityService.getLocalitiesByPostalCode(postalCode);
        return ResponseEntity.ok(localityResponses);
    }

    @GetMapping("/municipality/{municipalityId}")
    public ResponseEntity<Page<LocalityResponse>> getLocalitiesByMunicipality(
            @PathVariable String municipalityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<LocalityResponse> localities = localityService.getLocalitiesByMunicipalityId(municipalityId, pageable);

        return ResponseEntity.ok(localities);
    }


    @Operation(summary = "Obtener una lista paginada de localidades.")
    @GetMapping
    public ResponseEntity<Page<LocalityResponse>> getAllLocalities(
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<LocalityResponse> allLocalities = localityService.getAllLocalities(pageable, keyword);

        return ResponseEntity.ok(allLocalities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalityResponse> updateLocality(@PathVariable Long id, @Valid @RequestBody LocalityRequest updatedLocalityRequest) {
        LocalityResponse updatedLocality = localityService.updateLocality(id, updatedLocalityRequest);
        return ResponseEntity.ok(updatedLocality);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocalityById(@PathVariable Long id) {
        localityService.deleteLocalityById(id);
        return ResponseEntity.noContent().build();
    }
}
