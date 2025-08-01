package edu.mx.unsis.unsiSmile.controller.professors;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorGroupResponse;
import edu.mx.unsis.unsiSmile.service.professors.ProfessorGroupService;
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

@Tag(name = "PROFESSOR-GROUP")
@RestController
@RequestMapping("/unsismile/api/v1/professors/professor-groups")
@RequiredArgsConstructor
public class ProfessorGroupController {

    private final ProfessorGroupService professorGroupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfessorGroup(@RequestBody @Valid ProfessorGroupRequest request) {
        professorGroupService.createProfessorGroup(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorGroupResponse> getProfessorGroupById(@PathVariable Long id) {
        ProfessorGroupResponse response = professorGroupService.getProfessorGroupResponse(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorGroupResponse>> getAllProfessorGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "professor.person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam(required = false) String keyword) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProfessorGroupResponse> responses = professorGroupService.getAllProfessorGroup(pageable, keyword);

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProfessorGroup(@PathVariable Long id, @RequestBody @Valid ProfessorGroupRequest request) {
        professorGroupService.updateProfessorGroup(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProfessorGroup(@PathVariable Long id) {
        professorGroupService.deleteProfessorGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/professors/{professorId}")
    public ResponseEntity<Page<ProfessorGroupResponse>> getAllProfessorGroupsByProfessorId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "professor.person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @PathVariable String professorId) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProfessorGroupResponse> responses = professorGroupService.getAllProfessorGroupByProfessorId(pageable, professorId);

        return ResponseEntity.ok(responses);
    }
}