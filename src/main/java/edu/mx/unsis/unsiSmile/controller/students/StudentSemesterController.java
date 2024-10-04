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

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentSemesterRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentSemesterResponse;
import edu.mx.unsis.unsiSmile.service.students.StudentSemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unsismile/api/v1/student-semesters")
@RequiredArgsConstructor
public class StudentSemesterController {

    private final StudentSemesterService studentSemesterService;

    @PostMapping
    public ResponseEntity<Void> createStudentSemester(@Valid @RequestBody StudentSemesterRequest request) {
        studentSemesterService.createStudentSemester(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentSemesterResponse> getStudentSemesterByIds(@PathVariable Long id) {
        StudentSemesterResponse studentSemesterResponse = studentSemesterService.getStudentSemesterById(id);
        return ResponseEntity.ok(studentSemesterResponse);
    }

    @GetMapping
    public ResponseEntity<List<StudentSemesterResponse>> getAllStudentSemesters() {
        List<StudentSemesterResponse> allStudentSemesters = studentSemesterService.getAllStudentSemesters();
        return ResponseEntity.ok(allStudentSemesters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudentSemester(@PathVariable Long id,
            @Valid @RequestBody StudentSemesterRequest updatedRequest) {
        studentSemesterService.updateStudentSemester(id, updatedRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentSemester(@PathVariable Long id) {
        studentSemesterService.deleteStudentSemester(id);
        return ResponseEntity.noContent().build();
    }
}
