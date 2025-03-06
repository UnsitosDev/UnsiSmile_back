package edu.mx.unsis.unsiSmile.controller.patients;

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
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.GuardianResponse;
import edu.mx.unsis.unsiSmile.service.patients.GuardianService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/guardian")
public class GuardianController {

    private final GuardianService guardianService;

    public GuardianController(GuardianService guardianService) {
        this.guardianService = guardianService;
    }

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

    @GetMapping("/phone/{phone}")
    public ResponseEntity<GuardianResponse> getGuardianByPhone(@Valid @PathVariable String phone) {
        GuardianResponse guardianResponse = guardianService.getGuardianByPhone(phone);
        return ResponseEntity.ok(guardianResponse);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<GuardianResponse> getGuardianByEmail(@Valid @PathVariable String email) {
        GuardianResponse guardianResponse = guardianService.getGuardianByEmail(email);
        return ResponseEntity.ok(guardianResponse);
    }

    @GetMapping
    public ResponseEntity<List<GuardianResponse>> getAllGuardians() {
        List<GuardianResponse> allGuardians = guardianService.getAllGuardians();
        return ResponseEntity.ok(allGuardians);
    }

    @PutMapping("/{id}")
        public ResponseEntity<GuardianResponse> updateGuardian(@Valid @PathVariable Long id, @RequestBody GuardianRequest updatedGuardianRequest) {
        GuardianResponse updatedGuardian = guardianService.updateGuardian(id, updatedGuardianRequest);
        return ResponseEntity.ok(updatedGuardian);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuardianById(@Valid @PathVariable Long id) {
        guardianService.deleteGuardianById(id);
        return ResponseEntity.noContent().build();
    }
}