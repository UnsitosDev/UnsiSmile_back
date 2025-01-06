package edu.mx.unsis.unsiSmile.controller.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.MunicipalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.MunicipalityResponse;
import edu.mx.unsis.unsiSmile.service.addresses.MunicipalityService;
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
@RequestMapping("/unsismile/api/v1/municipalities")
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    @PostMapping
    public ResponseEntity<MunicipalityResponse> createMunicipality(@Valid @RequestBody MunicipalityRequest municipalityRequest) {
        MunicipalityResponse createdMunicipality = municipalityService.createMunicipality(municipalityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMunicipality);
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

    @Operation(summary = "Obtener una lista paginada de municipios por estado.")
    @GetMapping("/state/{stateId}")
    public ResponseEntity<Page<MunicipalityResponse>> getMunicipalitiesByState(
            @Valid @PathVariable String stateId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MunicipalityResponse> municipalityResponses =
                municipalityService.getMunicipalitiesByStateId(stateId, pageable);

        return ResponseEntity.ok(municipalityResponses);
    }


    @Operation(summary = "Obtener una lista paginada de municipios.")
    @GetMapping
    public ResponseEntity<Page<MunicipalityResponse>> getAllMunicipalities(
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MunicipalityResponse> municipalityResponses = municipalityService.getAllMunicipalities(pageable, keyword);

        return ResponseEntity.ok(municipalityResponses);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MunicipalityResponse> updateMunicipality(@Valid @PathVariable String id, @Valid @RequestBody MunicipalityRequest updatedMunicipalityRequest) {
        MunicipalityResponse updatedMunicipality = municipalityService.updateMunicipality(id, updatedMunicipalityRequest);
        return ResponseEntity.ok(updatedMunicipality);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMunicipalityById(@Valid @PathVariable String id) {
        municipalityService.deleteMunicipalityById(id);
        return ResponseEntity.noContent().build();
    }
}
