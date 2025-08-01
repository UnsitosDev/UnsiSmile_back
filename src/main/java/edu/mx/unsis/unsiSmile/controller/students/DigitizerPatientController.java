package edu.mx.unsis.unsiSmile.controller.students;

import edu.mx.unsis.unsiSmile.dtos.request.digitizers.DigitizerPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.digitizers.DigitizerPatientResponse;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.service.digitizers.DigitizerPatientService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Digitizer Patient", description = "Para gestionar relaciones entre capturadores y pacientes")
@RestController
@RequestMapping("/unsismile/api/v1/digitizer-patients")
@RequiredArgsConstructor
public class DigitizerPatientController {

    private final DigitizerPatientService digitizerPatientService;

    @Operation(summary = "Crear una relación entre capturador y paciente")
    @PostMapping
    public ResponseEntity<Void> createDigitizerPatient(@RequestBody @Valid DigitizerPatientRequest request) {
        digitizerPatientService.createDigitizerPatient(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener una relación capturador-paciente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DigitizerPatientResponse> getDigitizerPatientById(@PathVariable Long id) {
        DigitizerPatientResponse response = digitizerPatientService.getDigitizerPatientById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una lista paginada de pacientes asignados a un capturador")
    @GetMapping
    public ResponseEntity<Page<DigitizerPatientResponse>> getAllDigitizerPatients(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<DigitizerPatientResponse> results = digitizerPatientService.getAllDigitizerPatientsByDigitizer(username, pageable);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Obtener una lista paginada de capturadores que atienden a un paciente")
    @GetMapping("/patients/{patientId}/digitizers")
    public ResponseEntity<Page<MedicalRecordDigitizerModel>> getDigitizersByPatient(
            @PathVariable String patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "digitizer.student.enrollment") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MedicalRecordDigitizerModel> pageResult = digitizerPatientService.getDigitizersByPatient(pageable, patientId);
        return ResponseEntity.ok(pageResult);
    }

    @Operation(summary = "Eliminar una relación entre capturador y paciente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDigitizerPatient(@PathVariable Long id) {
        digitizerPatientService.deleteDigitizerPatient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}