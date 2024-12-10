package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.ReligionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.ReligionResponse;
import edu.mx.unsis.unsiSmile.service.patients.ReligionService;
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
@RequestMapping("/unsismile/api/v1/religions")
public class ReligionController {

    private final ReligionService religionService;

    public ReligionController(ReligionService religionService) {
        this.religionService = religionService;
    }

    @PostMapping
    public ResponseEntity<ReligionResponse> createReligion(@Valid @RequestBody ReligionRequest religionRequest) {
        ReligionResponse createdReligion = religionService.createReligion(religionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReligion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReligionResponse> getReligionById(@Valid @PathVariable Long id) {
        ReligionResponse religionResponse = religionService.getReligionById(id);
        return ResponseEntity.ok(religionResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ReligionResponse> getReligionByName(@Valid @PathVariable String name) {
        ReligionResponse religionResponse = religionService.getReligionByName(name);
        return ResponseEntity.ok(religionResponse);
    }

    @Operation(summary = "Obtener una lista paginada de religiones.")
    @GetMapping
    public ResponseEntity<Page<ReligionResponse>> getAllStudents(
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "religion") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ReligionResponse> religionResponses = religionService.getAllReligions(pageable, keyword);

        return ResponseEntity.ok(religionResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReligionResponse> updateReligion(@Valid @PathVariable Long id, @Valid @RequestBody ReligionRequest updatedReligionRequest) {
        ReligionResponse updatedReligion = religionService.updateReligion(id, updatedReligionRequest);
        return ResponseEntity.ok(updatedReligion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReligionById(@Valid @PathVariable Long id) {
        religionService.deleteReligionById(id);
        return ResponseEntity.noContent().build();
    }
}