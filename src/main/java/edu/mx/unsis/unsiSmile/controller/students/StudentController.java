package edu.mx.unsis.unsiSmile.controller.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.service.students.StudentService;
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
@RequestMapping("/unsismile/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createStudent(@RequestBody StudentRequest request) {
        studentService.createStudent(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{enrollment}")
    public StudentResponse getStudentByEnrollment(@PathVariable String enrollment) {
        return studentService.getStudentByEnrollment(enrollment);
    }

    @Operation(summary = "Obtener una lista paginada de estudiantes")
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Catalog key for filter", example = "person.email, enrollment")
            @RequestParam(defaultValue = "person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentResponse> studentResponses = studentService.getAllStudents(pageable);

        return ResponseEntity.ok(studentResponses);
    }

    @PutMapping("/{enrollment}")
    public ResponseEntity<Void> updateStudent(@PathVariable String enrollment,
            @RequestBody StudentRequest updatedStudentRequest) {
         studentService.updateStudent(enrollment, updatedStudentRequest);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{enrollment}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteStudentByEnrollment(@PathVariable String enrollment) {
        studentService.deleteStudentByEnrollment(enrollment);
        return ResponseEntity.noContent().build();
    }
}