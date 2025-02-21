package edu.mx.unsis.unsiSmile.controller.professors;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorClinicalAreaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PROFESSOR-CLINICAL-AREA")
@RestController
@RequestMapping("/unsismile/api/v1/professors/professor-clinical-areas")
@RequiredArgsConstructor
public class ProfessorClinicalAreaController {

    private final ProfessorClinicalAreaService professorClinicalAreaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfessorClinicalArea(@RequestBody @Valid ProfessorClinicalAreaRequest request) {
        professorClinicalAreaService.createProfessorClinicalArea(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorClinicalAreaResponse> getProfessorClinicalAreaById(@PathVariable Long id) {
        ProfessorClinicalAreaResponse response = professorClinicalAreaService.getProfessorClinicalAreaById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorClinicalAreaResponse>> getAllProfessorClinicalAreas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "clinicalArea.clinicalArea") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam(required = false) String keyword) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProfessorClinicalAreaResponse> responses = professorClinicalAreaService.getAllProfessorClinicalAreas(pageable, keyword);
        
        return ResponseEntity.ok(responses);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProfessorClinicalArea(@RequestBody @Valid ProfessorClinicalAreaRequest request) {
        professorClinicalAreaService.updateProfessorClinicalArea(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProfessorClinicalArea(@PathVariable Long id) {
        professorClinicalAreaService.deleteProfessorClinicalArea(id);
        return ResponseEntity.noContent().build();
    }
}