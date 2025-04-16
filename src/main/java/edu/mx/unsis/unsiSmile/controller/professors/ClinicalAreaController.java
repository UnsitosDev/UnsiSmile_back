package edu.mx.unsis.unsiSmile.controller.professors;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.service.professors.ClinicalAreaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unsismile/api/v1/clinical-areas")
@RequiredArgsConstructor
public class ClinicalAreaController {
    private final ClinicalAreaService clinicalAreaService;

    @Operation(summary = "Crea una nueva área clínica")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createClinicalArea(@RequestBody ClinicalAreaRequest request) {
        clinicalAreaService.createClinicalArea(request);
    }

    @Operation(summary = "Obtiene un área clínica por su ID")
    @GetMapping("/{idClinicalArea}")
    public ResponseEntity<ClinicalAreaResponse> getClinicalArea(@PathVariable Long idClinicalArea) {
        ClinicalAreaResponse clinicalArea = clinicalAreaService.getClinicalArea(idClinicalArea);
        return ResponseEntity.ok(clinicalArea);
    }

    @Operation(summary = "Obtiene todas las áreas clínicas, con paginación, ordenamiento y búsqueda opcional")
    @GetMapping
    public ResponseEntity<Page<ClinicalAreaResponse>> getAllClinicalAreas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "clinicalArea") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam(required = false) String keyword) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ClinicalAreaResponse> clinicalAreaResponses = clinicalAreaService.getAllClinicalAreas(pageable, keyword);

        return ResponseEntity.ok(clinicalAreaResponses);
    }

    @Operation(summary = "Actualiza una área clínica existente")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateClinicalArea(@RequestBody ClinicalAreaRequest request) {
        clinicalAreaService.updateClinicalArea(request);
    }

    @Operation(summary = "Elimina un área clínica por su ID")
    @DeleteMapping("/{idClinicalArea}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClinicalArea(@PathVariable Long idClinicalArea) {
        clinicalAreaService.deleteClinicalArea(idClinicalArea);
    }
}
