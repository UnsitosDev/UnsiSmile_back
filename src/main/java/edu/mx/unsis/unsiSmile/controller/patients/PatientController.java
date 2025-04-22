package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentPatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import edu.mx.unsis.unsiSmile.service.students.StudentPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unsismile/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final StudentPatientService studentPatientService;

    @PostMapping
    public ResponseEntity<Void> createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        patientService.createPatient(patientRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener una lista paginada de pacientes")
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @Parameter(description = "Optional parameter to specify a search criterion.")
            @RequestParam(required = false) String keyword) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PatientResponse> patientResponses = patientService.getAllPatients(pageable, keyword);

        return ResponseEntity.ok(patientResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable String id) {
        PatientResponse patientResponse = patientService.getPatientById(id);
        return ResponseEntity.ok(patientResponse);
    }

    // Implement similar methods for other search criteria like nationality, person,
    // address, marital status, occupation, ethnic group, religion, guardian, etc.

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable String id,
            @RequestBody PatientRequest updatedPatientRequest) {
        PatientResponse updatedPatient = patientService.updatePatient(id, updatedPatientRequest);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientById(@PathVariable String id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Asigna un estudiante a un paciente")
    @PostMapping("/students")
    public ResponseEntity<StudentPatientResponse> createStudent(
            @RequestBody StudentPatientRequest studentPatientRequest) {
        studentPatientService.createStudentPatient(studentPatientRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener una lista paginada de estudiantes que atienden a un paciente")
    @GetMapping("/{patientId}/students")
    public ResponseEntity<Page<StudentResponse>> getStudentsByPatient(
            @PathVariable String patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "student.enrollment") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentResponse> studentResponses = studentPatientService.getStudentsByPatient(pageable, patientId);

        return ResponseEntity.ok(studentResponses);
    }
}