package edu.mx.unsis.unsiSmile.controller.students;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.students.SemesterRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.SemesterResponse;
import edu.mx.unsis.unsiSmile.service.students.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unsismile/api/v1/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping
    public ResponseEntity<SemesterResponse> createSemester(@Valid @RequestBody SemesterRequest request) {
        SemesterResponse createdSemester = semesterService.createSemester(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSemester);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterResponse> getSemesterById(@PathVariable Long id) {
        SemesterResponse semesterResponse = semesterService.getSemesterById(id);
        return ResponseEntity.ok(semesterResponse);
    }

    @GetMapping
    public ResponseEntity<List<SemesterResponse>> getAllSemesters() {
        List<SemesterResponse> allSemesters = semesterService.getAllSemesters();
        return ResponseEntity.ok(allSemesters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterResponse> updateSemester(@PathVariable Long id, @Valid @RequestBody SemesterRequest updatedSemesterRequest) {
        SemesterResponse updatedSemester = semesterService.updateSemester(id, updatedSemesterRequest);
        return ResponseEntity.ok(updatedSemester);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSemesterById(@PathVariable Long id) {
        semesterService.deleteSemesterById(id);
        return ResponseEntity.noContent().build();
    }
}
