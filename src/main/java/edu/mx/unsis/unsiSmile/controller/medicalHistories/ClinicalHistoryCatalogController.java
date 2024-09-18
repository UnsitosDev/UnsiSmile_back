package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClinicalHistoryCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClinicalHistoryCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ClinicalHistoryCatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "------------CLINICAL HISTORY CATALOG")
@RestController
@RequestMapping("/api/clinical-history-catalog")
@RequiredArgsConstructor
public class ClinicalHistoryCatalogController {

    private final ClinicalHistoryCatalogService clinicalHistoryCatalogService;

    @Operation(summary = "Crea una historia clínica")
    @PostMapping
    public ResponseEntity<ClinicalHistoryCatalogResponse> save(@RequestBody ClinicalHistoryCatalogRequest request) {
        ClinicalHistoryCatalogResponse response = clinicalHistoryCatalogService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene una historia clínica con toda la configuración **necesita el id del paciente para devolverlo con respuestas")
    @GetMapping("/{id}")
    public ResponseEntity<ClinicalHistoryCatalogResponse> findById(@PathVariable Long id, @Nullable @RequestParam Long patientClinicalHistoryId) {
        ClinicalHistoryCatalogResponse response = clinicalHistoryCatalogService.findById(id, patientClinicalHistoryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una lista de historias clinicas pero con la configuración vacía")
    @GetMapping
    public ResponseEntity<List<ClinicalHistoryCatalogResponse>> findAll() {
        List<ClinicalHistoryCatalogResponse> response = clinicalHistoryCatalogService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una historia clínica, 'Verificar luego como eliminar de forma cascade'")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        clinicalHistoryCatalogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Obtiene una lista de catalogos y su relación con el paciente")
    @GetMapping("/search")
    public ResponseEntity<List<PatientClinicalHistoryResponse>> searchClinicalHistory(@RequestParam Long idPatient) {
        List<PatientClinicalHistoryResponse> response = clinicalHistoryCatalogService.searchClinicalHistory(idPatient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
