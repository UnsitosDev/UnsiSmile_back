package edu.mx.unsis.unsiSmile.controller.administrators;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.service.administrators.AdministratorService;
import lombok.RequiredArgsConstructor;

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

    @GetMapping
    public List<AdministratorResponse> getAllAdministrators() {
        return administratorService.getAllAdministrators();
    }

    //falta agregar el metodo de actualizar

    @DeleteMapping("/{employeeNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteAdministratorByEmployeeNumber(@PathVariable String employeeNumber) {
        administratorService.deleteAdministratorByEmployeeNumber(employeeNumber);
        return ResponseEntity.noContent().build();
    }
}
