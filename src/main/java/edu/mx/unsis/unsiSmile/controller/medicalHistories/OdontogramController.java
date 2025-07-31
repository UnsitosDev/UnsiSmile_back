package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramSimpleResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.OdontogramService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Odontogram Controller", description = "Endpoints for managing odontograms")
@RestController
@RequestMapping("/unsismile/api/v1/medical-records/odontograms")
public class OdontogramController {

    private final OdontogramService odontogramService;

    public OdontogramController(OdontogramService odontogramService) {
        this.odontogramService = odontogramService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontogramResponse> getOdontogramById(@Valid @PathVariable Long id) {
        OdontogramResponse odontogramResponse = odontogramService.getOdontogramById(id);
        return ResponseEntity.ok(odontogramResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createOdontogram(@Valid @RequestBody OdontogramRequest odontogramDTO) {
        odontogramService.saveOdontogram(odontogramDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<OdontogramSimpleResponse>> getOdontogramsByPatientId(@PathVariable String patientId) {
        List<OdontogramSimpleResponse> odontograms = odontogramService.getOdontogramsByPatientId(patientId);
        return ResponseEntity.ok(odontograms);
    }

    @GetMapping("/patients/{patientId}/latest")
    public ResponseEntity<OdontogramResponse> getLastOdontogramByPatientId(@PathVariable String patientId) {
        OdontogramResponse lastOdontogram = odontogramService.getlasOdontogramByPatientId(patientId);
        return ResponseEntity.ok(lastOdontogram);
    }


}
