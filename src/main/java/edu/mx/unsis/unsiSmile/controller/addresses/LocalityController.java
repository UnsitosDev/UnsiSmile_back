package edu.mx.unsis.unsiSmile.controller.addresses;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.LocalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.LocalityResponse;
import edu.mx.unsis.unsiSmile.model.addresses.MunicipalityModel;
import edu.mx.unsis.unsiSmile.service.addresses.LocalityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/address/locality")
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
    public ResponseEntity<LocalityResponse> updateLocality(@Valid @PathVariable String id, @Valid @RequestBody LocalityRequest updatedLocalityRequest) {
        LocalityResponse updatedLocality = localityService.updateLocality(id, updatedLocalityRequest);
        return ResponseEntity.ok(updatedLocality);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocalityById(@Valid @PathVariable String id) {
        localityService.deleteLocalityById(id);
        return ResponseEntity.noContent().build();
    }

    private MunicipalityModel getMunicipalityById(String municipalityId) {
        // Implement the logic to fetch the MunicipalityModel by ID
        // This is just a placeholder, you need to provide the actual implementation
        return new MunicipalityModel();
    }
}
