package edu.mx.unsis.unsiSmile.controller.administrators;

import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.service.administrators.AdministratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unsismile/api/v1/administrators")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdministratorResponse createAdministrator(@RequestBody AdministratorRequest request) {
        return administratorService.createAdministrator(request);
    }

    @GetMapping("/{employeeNumber}")
    public AdministratorResponse getAdministratorByEmployeeNumber(@PathVariable String employeeNumber) {
        return administratorService.getAdministratorByEmployeeNumber(employeeNumber);
    }

    @Operation(summary = "Obtener una lista paginada de administradores.")
    @GetMapping
    public ResponseEntity<Page<AdministratorResponse>> getAllAdministrators(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field key for filter", example = "person.email, employeeNumber") @RequestParam(defaultValue = "person.firstName") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @Parameter(description = "Optional parameter to specify a search criterion.") @RequestParam(required = false) String keyword) {
        Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AdministratorResponse> administratorResponses = administratorService.getAllAdministrators(pageable,
                keyword);

        return ResponseEntity.ok(administratorResponses);
    }

    @PatchMapping("/{employeeNumber}")
    public AdministratorResponse updateAdministrator(@PathVariable String employeeNumber,
            @RequestBody AdministratorUpdateRequest request) {
        return administratorService.updateAdministrator(employeeNumber, request);
    }

    @DeleteMapping("/{employeeNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteAdministratorByEmployeeNumber(@PathVariable String employeeNumber) {
        administratorService.deleteAdministratorByEmployeeNumber(employeeNumber);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cambiar estado de activo/inactivo de un administrador")
    @PatchMapping("/{employeeNumber}/toggle-status")
    public ResponseEntity<Void> updateAdministratorStatus(@PathVariable String employeeNumber) {
        administratorService.updateAdministratorStatus(employeeNumber);
        return ResponseEntity.noContent().build();
    }
}