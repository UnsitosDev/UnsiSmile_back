package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientMedicalRecordResponse;
import edu.mx.unsis.unsiSmile.service.medicalrecords.MedicalRecordCatalogDigitizerService;
import edu.mx.unsis.unsiSmile.service.patients.PatientMedicalRecordDigitizerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Digitalizar Historias Clínicas")
@RestController
@RequestMapping("/unsismile/api/v1/digitizers/patients/medical-records")
@RequiredArgsConstructor
public class MedicalRecordCatalogDigitizerController {

    private final MedicalRecordCatalogDigitizerService medicalRecordCatalogDigitizerService;
    private final PatientMedicalRecordDigitizerService patientMedicalRecordDigitizerService;

    @Operation(summary = "Obtiene una lista de historías clínicas y su relación con el paciente.")
    @GetMapping()
    public ResponseEntity<List<PatientMedicalRecordResponse>> searchMedicalRecords(@RequestParam String idPatient) {
        List<PatientMedicalRecordResponse> response = medicalRecordCatalogDigitizerService.searchCMedicalRecordsByPatient(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Crea la relación entre el paciente y la historia clínica.")
    @PostMapping()
    public ResponseEntity<MedicalRecordCatalogResponse> createPatientMedicalRecord(
            @RequestParam String idPatient,
            @RequestParam Long idMedicalRecordCatalog) {
        MedicalRecordCatalogResponse response = patientMedicalRecordDigitizerService.save(idPatient, idMedicalRecordCatalog);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}