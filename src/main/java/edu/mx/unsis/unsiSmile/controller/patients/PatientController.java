package edu.mx.unsis.unsiSmile.controller.patients;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        PatientResponse createdPatient = patientService.createPatient(patientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getPatients() {
        List<PatientResponse> patientResponse = patientService.getAllPatients();
        return ResponseEntity.ok(patientResponse);
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

    // Implement similar methods for other search criteria like nationality, person, address, marital status, occupation, ethnic group, religion, guardian, etc.

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest updatedPatientRequest) {
        PatientResponse updatedPatient = patientService.updatePatient(id, updatedPatientRequest);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientById(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }
}
