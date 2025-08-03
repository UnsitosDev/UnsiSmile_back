package edu.mx.unsis.unsiSmile.controller.administrators;

import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.service.administrators.MedicalAdministratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Administradores Clínicos", description = "Operaciones relacionadas con los administradores clínicos del sistema.")
@RestController
@RequestMapping("/unsismile/api/v1/medical-administrators")
@RequiredArgsConstructor
@Validated
public class MedicalAdministratorController {

    private final MedicalAdministratorService medicalAdministratorService;

    @Operation(summary = "Crear un nuevo administrador clínico.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdministratorResponse createAdministrator(
            @Valid @RequestBody AdministratorRequest request) {
        return medicalAdministratorService.createMedicalAdministrator(request);
    }

    @Operation(summary = "Obtener un administrador clínico por número de empleado.")
    @GetMapping("/{employeeNumber}")
    public ResponseEntity<AdministratorResponse> getAdministratorByEmployeeNumber(
            @Parameter(description = "Número de empleado del administrador clínico.", required = true)
            @PathVariable String employeeNumber) {
        AdministratorResponse response = medicalAdministratorService.getMedicalAdministratorByEmployeeNumber(employeeNumber);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una lista paginada de administradores clínicos.")
    @GetMapping
    public ResponseEntity<Page<AdministratorResponse>> getAllAdministrators(
            @Parameter(description = "Número de página (inicia en 0).") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de elementos por página.") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar los resultados.", example = "person.email")
            @RequestParam(defaultValue = "person.firstName") String order,
            @Parameter(description = "Orden ascendente (true) o descendente (false).") @RequestParam(defaultValue = "true") boolean asc,
            @Parameter(description = "Criterio de búsqueda opcional.") @RequestParam(required = false) String keyword) {

        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AdministratorResponse> administratorResponses = medicalAdministratorService
                .getAllMedicalAdministrators(pageable, keyword);

        return ResponseEntity.ok(administratorResponses);
    }

    @Operation(summary = "Actualizar un administrador clínico existente.")
    @PatchMapping("/{employeeNumber}")
    public ResponseEntity<AdministratorResponse> updateAdministrator(
            @Parameter(description = "Número de empleado del administrador clínico a actualizar.") @PathVariable String employeeNumber,
            @RequestBody AdministratorUpdateRequest request) {
        AdministratorResponse response = medicalAdministratorService.updateMedicalAdministrator(employeeNumber, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un administrador clínico por número de empleado.")
    @DeleteMapping("/{employeeNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteAdministratorByEmployeeNumber(
            @Parameter(description = "Número de empleado del administrador clínico a eliminar.") @PathVariable String employeeNumber) {
        medicalAdministratorService.deleteMedicalAdministratorByEmployeeNumber(employeeNumber);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cambiar estado de activo/inactivo de un administrador clínico.")
    @PatchMapping("/{employeeNumber}/toggle-status")
    public ResponseEntity<Void> updateAdministratorStatus(
            @Parameter(description = "Número de empleado del administrador clínico.") @PathVariable String employeeNumber) {
        medicalAdministratorService.updateMedicalAdministratorStatus(employeeNumber);
        return ResponseEntity.noContent().build();
    }
}