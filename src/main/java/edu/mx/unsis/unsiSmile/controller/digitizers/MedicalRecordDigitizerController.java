package edu.mx.unsis.unsiSmile.controller.digitizers;

import edu.mx.unsis.unsiSmile.dtos.request.digitizers.MedicalRecordDigitizerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.digitizers.MedicalRecordDigitizerResponse;
import edu.mx.unsis.unsiSmile.service.digitizers.MedicalRecordDigitizerService;
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

@Tag(name = "Capturadores de expedientes clínicos", description = "Gestión de usuarios capturadores.")
@RestController
@RequestMapping("/unsismile/api/v1/medical-record-digitizers")
@RequiredArgsConstructor
public class MedicalRecordDigitizerController {

    private final MedicalRecordDigitizerService digitizerService;

    @Operation(summary = "Crea un nuevo capturador de expedientes.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody MedicalRecordDigitizerRequest request) {
        digitizerService.createDigitizer(request);
    }

    @Operation(summary = "Obtiene un capturador por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDigitizerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(digitizerService.getDigitizerById(id));
    }

    @Operation(summary = "Elimina un capturador por su ID.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        digitizerService.deleteDigitizer(id);
    }

    @Operation(summary = "Obtiene una lista paginada de capturadores con filtros opcionales.")
    @GetMapping
    public ResponseEntity<Page<MedicalRecordDigitizerResponse>> getAllMedicalRecordDigitizer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "user.username") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam(required = false) String keyword) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(digitizerService.getAllDigitizers(pageable, keyword));
    }

    @Operation(summary = "Cambia el estado de activo/inactivo de un capturador.")
    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@PathVariable Long id) {
        digitizerService.changeDigitizerStatus(id);
    }
}