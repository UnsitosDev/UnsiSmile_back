package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.NationalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.NationalityResponse;
import edu.mx.unsis.unsiSmile.service.patients.NationalityService;
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

@RestController
@RequestMapping("/unsismile/api/v1/nationalities")
public class NationalityController {

    private final NationalityService nationalityService;

    public NationalityController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    @PostMapping
    public ResponseEntity<NationalityResponse> createNationality(@Valid @RequestBody NationalityRequest nationalityRequest) {
        NationalityResponse createdNationality = nationalityService.createNationality(nationalityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNationality);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NationalityResponse> getNationalityById(@Valid @PathVariable Long id) {
        NationalityResponse nationalityResponse = nationalityService.getNationalityById(id);
        return ResponseEntity.ok(nationalityResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<NationalityResponse> getNationalityByName(@Valid @PathVariable String name) {
        NationalityResponse nationalityResponse = nationalityService.getNationalityByName(name);
        return ResponseEntity.ok(nationalityResponse);
    }

    @Operation(summary = "Obtener una lista paginada de nacionalidades")
    @GetMapping
    public ResponseEntity<Page<NationalityResponse>> getAllStudents(
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nationality") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NationalityResponse> nationalityResponses = nationalityService.getAllNationalities(pageable, keyword);

        return ResponseEntity.ok(nationalityResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NationalityResponse> updateNationality(@Valid @PathVariable Long id, @Valid @RequestBody NationalityRequest updatedNationalityRequest) {
        NationalityResponse updatedNationality = nationalityService.updateNationality(id, updatedNationalityRequest);
        return ResponseEntity.ok(updatedNationality);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNationalityById(@Valid @PathVariable Long id) {
        nationalityService.deleteNationalityById(id);
        return ResponseEntity.noContent().build();
    }
}