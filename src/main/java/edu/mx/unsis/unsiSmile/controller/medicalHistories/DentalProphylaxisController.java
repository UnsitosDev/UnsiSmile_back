package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.DentalProphylaxisRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.DentalProphylaxisResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothCodeResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.DentalProphylaxisService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.SOHIService;
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

import java.util.List;

@Tag(name = "DENTAL PROPHYLAXIS")
@RestController
@RequiredArgsConstructor
@RequestMapping("/unsismile/api/v1/medical-histories/dental-prophylaxis")
public class DentalProphylaxisController {

    private final DentalProphylaxisService dentalProphylaxisService;
    private final SOHIService sohiService;

    @PostMapping
    public ResponseEntity<Void> createDentalProphylaxis(@Valid @RequestBody DentalProphylaxisRequest dentalProphylaxisDTO) {
        dentalProphylaxisService.saveDentalProphylaxis(dentalProphylaxisDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DentalProphylaxisResponse>> getAllDentalProphylaxis() {
        List<DentalProphylaxisResponse> allDentalProphylaxis = dentalProphylaxisService.getAllDentalProphylaxis();
        return ResponseEntity.ok(allDentalProphylaxis);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDentalProphylaxis(@Valid @PathVariable Long id) {
        dentalProphylaxisService.deleteDentalProphylaxis(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener una lista paginada de profilaxis dental por tratamiento")
    @GetMapping("/treatments/{idTreatment}")
    public ResponseEntity<Page<DentalProphylaxisResponse>> getAllByTreatmentId(
            @PathVariable Long idTreatment,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<DentalProphylaxisResponse> result = dentalProphylaxisService
                .getDentalProphylaxisByTreatmentId(idTreatment, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Obtener una lista paginada de profilaxis dental por paciente")
    public ResponseEntity<Page<DentalProphylaxisResponse>> getAllByPatientId(
            @PathVariable String patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<DentalProphylaxisResponse> result = dentalProphylaxisService
                .getDentalProphylaxisByPatientId(patientId, pageable);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Crea un registro de índice de higiene oral simplificado.")
    @PostMapping("/sohi")
    public ResponseEntity<Void> create(@Valid @RequestBody ToothCodeRequest request) {
        sohiService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Obtiene un registro de índice de higiene oral simplificado por ID de tratamiento.")
    @GetMapping("/sohi/treatment/{id}")
    public ResponseEntity<ToothCodeResponse> getByTreatmentId(@PathVariable("id") Long idTreatment) {
        ToothCodeResponse response = sohiService.getByTreatmentId(idTreatment);
        return ResponseEntity.ok(response);
    }
}