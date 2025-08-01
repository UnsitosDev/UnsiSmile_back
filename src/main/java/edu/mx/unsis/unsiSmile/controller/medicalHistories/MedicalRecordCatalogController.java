package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.forms.catalogs.MedicalRecordCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.MedicalRecordListResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientMedicalRecordRes;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientMedicalRecordResponse;
import edu.mx.unsis.unsiSmile.model.forms.sections.MedicalRecordSectionModel;
import edu.mx.unsis.unsiSmile.model.enums.EMedicalRecords;
import edu.mx.unsis.unsiSmile.service.medicalHistories.MedicalRecordCatalogService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.MedicalRecordSectionService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientMedicalRecordDigitizerService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientMedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CATÁLOGO DE HISTORIAS CLÍNICAS")
@RestController
@RequestMapping("/unsismile/api/v1/medical-records")
@RequiredArgsConstructor
public class MedicalRecordCatalogController {

    private final MedicalRecordCatalogService medicalRecordCatalogService;
    private final PatientMedicalRecordService patientMedicalRecordService;
    private final MedicalRecordSectionService medicalRecordSectionService;
    private final PatientMedicalRecordDigitizerService patientMedicalRecordDigitizerService;

    @Operation(summary = "Crea una historia clínica.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody MedicalRecordCatalogRequest request) {
        medicalRecordCatalogService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una historia clínica con la configuración.")
    @GetMapping("/{idPatientMedicalRecord}/patients/{idPatient}")
    public ResponseEntity<MedicalRecordCatalogResponse> findById(
            @PathVariable Long idPatientMedicalRecord,
            @PathVariable String idPatient) {
        MedicalRecordCatalogResponse response = medicalRecordCatalogService.findById(idPatientMedicalRecord, idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de historias clinicas sin configuración.")
    @GetMapping
    public ResponseEntity<List<MedicalRecordCatalogResponse>> findAll() {
        List<MedicalRecordCatalogResponse> response = medicalRecordCatalogService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una historia clínica.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        medicalRecordCatalogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtiene una lista de historías clínicas y su relación con el paciente.")
    @GetMapping("/patient-medical-records")
    public ResponseEntity<List<PatientMedicalRecordResponse>> searchMedicalRecords(@RequestParam String idPatient) {
        List<PatientMedicalRecordResponse> response = medicalRecordCatalogService.searchMedicalRecords(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la historia clínica general del paciente.")
    @GetMapping("/general")
    public ResponseEntity<MedicalRecordCatalogResponse> searchGeneralMedicalRecord(@RequestParam String idPatient) {
        MedicalRecordCatalogResponse response = medicalRecordCatalogService.searchGeneralMedicalRecord(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crear la historia clínica general del paciente.")
    @PostMapping("/general")
    public ResponseEntity<MedicalRecordCatalogResponse> createNewGeneralMedicalRecord(@RequestParam String idPatient) {
        MedicalRecordCatalogResponse response = medicalRecordCatalogService.createNewGeneralMedicalRecord(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crea la relación entre el paciente y la historia clínica.")
    @PostMapping("/patient-medical-record")
    public ResponseEntity<PatientMedicalRecordRes> createPatientMedicalRecord(
            @RequestParam String idPatient,
            @RequestParam Long idMedicalRecordCatalog) {
        PatientMedicalRecordRes response = patientMedicalRecordService.createPatientMedicalRecord(idPatient, idMedicalRecordCatalog);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Crea la relación entre una sección (formulario) y la historia clínica.")
    @PostMapping("/medical-record-section")
    public ResponseEntity<MedicalRecordSectionModel> createMedicalRecordSection(
            @RequestParam Long idMedicalRecordCatalog,
            @RequestParam String idSection
    ) {
        MedicalRecordSectionModel response = medicalRecordSectionService.save(idMedicalRecordCatalog, idSection);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una historia clínica por tipo de HC y paciente.")
    @GetMapping("/catalog/{medicalRecord}/patients/{idPatient}")
    public ResponseEntity<MedicalRecordCatalogResponse> findByMedicalRecordAndPatient(
            @PathVariable EMedicalRecords medicalRecord,
            @PathVariable String idPatient) {
        MedicalRecordCatalogResponse response = medicalRecordCatalogService.findByMedicalRecordAndPatient(medicalRecord, idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un historial paginado de las historias clínicas capturadas junto a los tratamientos")
    @GetMapping("/patient-medical-records/patients/{idPatient}")
    public ResponseEntity<Page<MedicalRecordListResponse>> findPatientMedicalRecords(
            @PathVariable String idPatient,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String order,
            @RequestParam(defaultValue = "true") boolean asc) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MedicalRecordListResponse> response = patientMedicalRecordDigitizerService
                .findPatientMedicalRecords(idPatient, pageable);

        return ResponseEntity.ok(response);
    }
}
