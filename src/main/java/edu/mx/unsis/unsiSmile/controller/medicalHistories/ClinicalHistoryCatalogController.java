package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClinicalHistoryCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClinicalHistoryCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientMedicalRecordRes;
import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.EMedicalRecords;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ClinicalHistoryCatalogService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ClinicalHistorySectionService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PatientClinicalHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CLINICAL HISTORY CATALOG")
@RestController
@RequestMapping("/unsismile/api/v1/clinical-histories")
@RequiredArgsConstructor
public class ClinicalHistoryCatalogController {

    private final ClinicalHistoryCatalogService clinicalHistoryCatalogService;
    private final PatientClinicalHistoryService patientClinicalHistoryService;
    private final ClinicalHistorySectionService clinicalHistorySectionService;

    @Operation(summary = "Crea una historia clínica.")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ClinicalHistoryCatalogRequest request) {
        clinicalHistoryCatalogService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una historia clínica con la configuración.")
    @GetMapping("/{idPatientMedicalRecord}/patients/{idPatient}")
    public ResponseEntity<ClinicalHistoryCatalogResponse> findById(
            @PathVariable Long idPatientMedicalRecord,
            @PathVariable String idPatient) {
        ClinicalHistoryCatalogResponse response = clinicalHistoryCatalogService.findById(idPatientMedicalRecord, idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de historias clinicas sin configuración.")
    @GetMapping
    public ResponseEntity<List<ClinicalHistoryCatalogResponse>> findAll() {
        List<ClinicalHistoryCatalogResponse> response = clinicalHistoryCatalogService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una historia clínica.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clinicalHistoryCatalogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtiene una lista de historías clínicas y su relación con el paciente.")
    @GetMapping("/patient-clinical-histories")
    public ResponseEntity<List<PatientClinicalHistoryResponse>> searchClinicalHistory(@RequestParam String idPatient) {
        List<PatientClinicalHistoryResponse> response = clinicalHistoryCatalogService.searchClinicalHistory(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la historia clínica general del paciente.")
    @GetMapping("/general")
    public ResponseEntity<ClinicalHistoryCatalogResponse> searchGeneralMedicalRecord(@RequestParam String idPatient) {
        ClinicalHistoryCatalogResponse response = clinicalHistoryCatalogService.searchGeneralMedicalRecord(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crear la historia clínica general del paciente.")
    @PostMapping("/general")
    public ResponseEntity<ClinicalHistoryCatalogResponse> createNewGeneralMedicalRecord(@RequestParam String idPatient) {
        ClinicalHistoryCatalogResponse response = clinicalHistoryCatalogService.createNewGeneralMedicalRecord(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crea la relación entre el paciente y la historia clínica.")
    @PostMapping("/patient-clinical-history")
    public ResponseEntity<PatientMedicalRecordRes> createPatientClinicalHistory(
            @RequestParam String idPatient,
            @RequestParam Long idClinicalHistory) {
        PatientMedicalRecordRes response = patientClinicalHistoryService.createPatientMedicalRecord(idPatient, idClinicalHistory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Crea la relación entre una sección (formulario) y la historia clínica.")
    @PostMapping("/clinial-history-section")
    public ResponseEntity<ClinicalHistorySectionModel> createClinicalHistorySection(
            @RequestParam Long idClinicalHistory,
            @RequestParam Long idSection
    ) {
        ClinicalHistorySectionModel response = clinicalHistorySectionService.save(idClinicalHistory, idSection);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una historia clínica por tipo de HC y paciente.")
    @GetMapping("/catalog/{medicalRecord}/patients/{idPatient}")
    public ResponseEntity<ClinicalHistoryCatalogResponse> findByMedicalRecordAndPatient(
            @PathVariable EMedicalRecords medicalRecord,
            @PathVariable String idPatient) {
        ClinicalHistoryCatalogResponse response = clinicalHistoryCatalogService.findByMedicalRecordAndPatient(medicalRecord, idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
