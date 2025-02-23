package edu.mx.unsis.unsiSmile.controller.professors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.service.professors.ClinicalAreaService;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unsismile/api/v1/professors/clinical-areas")
@RequiredArgsConstructor
public class ClinicalAreaController {
    private final ClinicalAreaService clinicalAreaService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createClinicalArea(@RequestBody ClinicalAreaRequest request) {
        clinicalAreaService.createClinicalArea(request);
    }

    @GetMapping("/{idClinicalArea}")
    public ResponseEntity<ClinicalAreaResponse> getClinicalArea(@PathVariable Long idClinicalArea) {
        ClinicalAreaResponse clinicalArea = clinicalAreaService.getClinicalArea(idClinicalArea);
        return ResponseEntity.ok(clinicalArea);
    }

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

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateClinicalArea(@RequestBody ClinicalAreaRequest request) {
        clinicalAreaService.updateClinicalArea(request);
    }

    @DeleteMapping("/{idClinicalArea}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClinicalArea(@PathVariable Long idClinicalArea) {
        clinicalAreaService.deleteClinicalArea(idClinicalArea);
    }
}
