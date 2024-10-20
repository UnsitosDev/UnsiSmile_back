package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentPatientResponse;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import edu.mx.unsis.unsiSmile.service.students.StudentPatientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/patients")
public class PatientController {

    private final PatientService patientService;
    private final StudentPatientService studentPatientService;

    public PatientController(PatientService patientService, StudentPatientService studentPatientService) {
        this.patientService = patientService;
        this.studentPatientService = studentPatientService;
    }

    @PostMapping
    public ResponseEntity<Void> createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        patientService.createPatient(patientRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PatientResponse> patientResponses = patientService.getAllPatients(pageable);

        return ResponseEntity.ok(patientResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        PatientResponse patientResponse = patientService.getPatientById(id);
        return ResponseEntity.ok(patientResponse);
    }

    @GetMapping("/admissionDate/{admissionDate}")
    public ResponseEntity<List<PatientResponse>> getPatientsByAdmissionDate(@PathVariable LocalDate admissionDate) {
        List<PatientResponse> patients = patientService.getPatientsByAdmissionDate(admissionDate);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/isMinor/{isMinor}")
    public ResponseEntity<List<PatientResponse>> getPatientsByIsMinor(@PathVariable Boolean isMinor) {
        List<PatientResponse> patients = patientService.getPatientsByIsMinor(isMinor);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/hasDisability/{hasDisability}")
    public ResponseEntity<List<PatientResponse>> getPatientsByHasDisability(@PathVariable Boolean hasDisability) {
        List<PatientResponse> patients = patientService.getPatientsByHasDisability(hasDisability);
        return ResponseEntity.ok(patients);
    }

    // Implement similar methods for other search criteria like nationality, person,
    // address, marital status, occupation, ethnic group, religion, guardian, etc.

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id,
            @Valid @RequestBody PatientRequest updatedPatientRequest) {
        PatientResponse updatedPatient = patientService.updatePatient(id, updatedPatientRequest);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientById(@PathVariable Long id) {
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
}
