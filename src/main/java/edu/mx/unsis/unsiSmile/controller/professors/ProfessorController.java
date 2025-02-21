package edu.mx.unsis.unsiSmile.controller.professors;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unsismile/api/v1/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfessor(@RequestBody ProfessorRequest request) {
        professorService.createProfessor(request);
    }

    @GetMapping("/{employeeNumber}")
    public ResponseEntity<ProfessorResponse> getProfessorByEmployeeNumber(@PathVariable String employeeNumber) {
        ProfessorResponse professor = professorService.getProfessor(employeeNumber);
        return ResponseEntity.ok(professor);
    }

    @Operation(summary = "Obtener una lista paginada de profesores.")
    @GetMapping
    public ResponseEntity<Page<ProfessorResponse>> getAllProfessors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for filter", example = "person.email, employeeNumber")
            @RequestParam(defaultValue = "person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(required = false) String keyword) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProfessorResponse> professorResponses = professorService.getAllProfessors(pageable, keyword);

        return ResponseEntity.ok(professorResponses);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProfessor(@RequestBody ProfessorRequest request) {
        professorService.updateProfessor(request);
    }

    @DeleteMapping("/{employeeNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteProfessorByEmployeeNumber(@PathVariable String employeeNumber) {
        professorService.deleteProfessor(employeeNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{employeeNumber}/toggle-status")
    @ResponseStatus(HttpStatus.OK)
    public void toggleProfessorStatus(@PathVariable String employeeNumber) {
        professorService.toggleProfessorStatus(employeeNumber);
    }
}
