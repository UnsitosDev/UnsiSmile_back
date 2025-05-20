package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FluorosisRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FluorosisResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.FluorosisService;
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

@Tag(name = "Fluorosis")
@RestController
@RequiredArgsConstructor
@RequestMapping("/unsismile/api/v1/medical-records/fluorosis")
public class FluorosisController {

    private final FluorosisService fluorosisService;

    @Operation(summary = "Crear un nuevo registro de fluorosis")
    @PostMapping
    public ResponseEntity<Void> createFluorosis(@Valid @RequestBody FluorosisRequest fluorosisDTO) {
        fluorosisService.saveFluorosis(fluorosisDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene un listado de todas las fluorosis")
    @GetMapping
    public ResponseEntity<List<FluorosisResponse>> getAllFluorosis() {
        List<FluorosisResponse> allFluorosis = fluorosisService.getAllFluorosis();
        return ResponseEntity.ok(allFluorosis);
    }

    @Operation(summary = "Elimina un registro de fluorosis por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFluorosis(@Valid @PathVariable Long id) {
        fluorosisService.deleteFluorosis(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener un registro de fluorosis por id de la sección del formulario y id de paciente")
    @GetMapping("/treatments/{idTreatment}")
    public ResponseEntity<FluorosisResponse> getFluorosisByTreatmentId(@PathVariable Long idTreatment) {
        FluorosisResponse fluorosisResponse = fluorosisService.getFluorosisByTreatmentId(idTreatment);
        return ResponseEntity.ok(fluorosisResponse);
    }

    @GetMapping("/patients/{patientId}")
    @Operation(summary = "Obtener una lista paginada de fluorosis por paciente")
    public ResponseEntity<Page<FluorosisResponse>> getAllByPatientId(
            @PathVariable String patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean asc) {

        Sort sort = asc ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<FluorosisResponse> result = fluorosisService
                .getFluorosisByPatientId(patientId, pageable);

        return ResponseEntity.ok(result);
    }
}