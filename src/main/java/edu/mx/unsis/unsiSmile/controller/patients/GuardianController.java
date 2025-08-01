package edu.mx.unsis.unsiSmile.controller.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.GuardianResponse;
import edu.mx.unsis.unsiSmile.service.patients.GuardianService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/unsismile/api/v1/guardians")
public class GuardianController {

    private final GuardianService guardianService;

    @PostMapping
    public ResponseEntity<GuardianResponse> createGuardian(@Valid @RequestBody GuardianRequest guardianRequest) {
        GuardianResponse createdGuardian = guardianService.createGuardian(guardianRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGuardian);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuardianResponse> getGuardianById(@Valid @PathVariable Long id) {
        GuardianResponse guardianResponse = guardianService.getGuardianById(id);
        return ResponseEntity.ok(guardianResponse);
    }

    @GetMapping
    public ResponseEntity<List<GuardianResponse>> getAllGuardians() {
        List<GuardianResponse> allGuardians = guardianService.getAllGuardians();
        return ResponseEntity.ok(allGuardians);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuardianResponse> updateGuardian(@Valid @PathVariable Long id,
            @RequestBody GuardianRequest updatedGuardianRequest) {
        GuardianResponse updatedGuardian = guardianService.updateGuardian(id, updatedGuardianRequest);
        return ResponseEntity.ok(updatedGuardian);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuardianById(@Valid @PathVariable Long id) {
        guardianService.deleteGuardianById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/CURP/{curp}")
    public ResponseEntity<GuardianResponse> getGuardianByCurp(@PathVariable String curp) {
        GuardianResponse guardianResponse = guardianService.getGuardianByCurp(curp);
        return ResponseEntity.ok(guardianResponse);
    }
}