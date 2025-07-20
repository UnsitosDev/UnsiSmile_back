package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.MedicalRecordCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientMedicalRecordRes;
import edu.mx.unsis.unsiSmile.model.MedicalRecordSectionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.EMedicalRecords;
import edu.mx.unsis.unsiSmile.service.medicalHistories.MedicalRecordCatalogService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.MedicalRecordSectionService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientMedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/patient-clinical-histories")
    public ResponseEntity<List<PatientClinicalHistoryResponse>> searchMedicalRecords(@RequestParam String idPatient) {
        List<PatientClinicalHistoryResponse> response = medicalRecordCatalogService.searchMedicalRecords(idPatient);
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
    @PostMapping("/patient-clinical-history")
    public ResponseEntity<PatientMedicalRecordRes> createPatientMedicalRecord(
            @RequestParam String idPatient,
            @RequestParam Long idMedicalRecordCatalog) {
        PatientMedicalRecordRes response = patientMedicalRecordService.createPatientMedicalRecord(idPatient, idMedicalRecordCatalog);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Crea la relación entre una sección (formulario) y la historia clínica.")
    @PostMapping("/clinial-history-section")
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
}
