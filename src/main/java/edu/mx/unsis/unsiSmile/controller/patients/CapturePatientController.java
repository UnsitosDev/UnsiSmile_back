package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.CapturePatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.service.patients.CapturePatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Patients", description = "Operaciones de pacientes para captura de información.")
@RestController
@RequestMapping("/unsismile/api/v1/digitizers/patients")
@RequiredArgsConstructor
public class CapturePatientController {

    private final CapturePatientService patientService;

    @Operation(summary = "Crea un paciente para capturar su información.")
    @PostMapping
    public ResponseEntity<Void> createPatient(@Valid @RequestBody CapturePatientRequest patientRequest) {
        patientService.createPatient(patientRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza los datos de un paciente.")
    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable String id,
            @RequestBody CapturePatientRequest updatedPatientRequest) {
        PatientResponse updatedPatient = patientService.updatePatient(id, updatedPatientRequest);
        return ResponseEntity.ok(updatedPatient);
    }
}