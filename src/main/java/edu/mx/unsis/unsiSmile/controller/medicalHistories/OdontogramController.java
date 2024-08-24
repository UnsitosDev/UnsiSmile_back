package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import java.util.List;

import edu.mx.unsis.unsiSmile.model.medicalHistories.OdontogramModel;
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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.OdontogramService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/odontograms")
public class OdontogramController {

    private final OdontogramService odontogramService;

    public OdontogramController(OdontogramService odontogramService) {
        this.odontogramService = odontogramService;
    }

    @PostMapping
    public ResponseEntity<OdontogramResponse> createOdontogram(@Valid @RequestBody OdontogramRequest request) {
        OdontogramResponse createdOdontogramResponse = odontogramService.createOdontogram(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOdontogramResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontogramResponse> getOdontogramById(@Valid @PathVariable Long id) {
        OdontogramResponse odontogramResponse = odontogramService.getOdontogramById(id);
        return ResponseEntity.ok(odontogramResponse);
    }

    @GetMapping
    public ResponseEntity<List<OdontogramResponse>> getAllOdontograms() {
        List<OdontogramResponse> allOdontograms = odontogramService.getAllOdontograms();
        return ResponseEntity.ok(allOdontograms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OdontogramResponse> updateOdontogram(@Valid @PathVariable Long id,
                                                                @Valid @RequestBody OdontogramRequest updateRequest) {
        OdontogramResponse updatedOdontogramResponse = odontogramService.updateOdontogram(id, updateRequest);
        return ResponseEntity.ok(updatedOdontogramResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOdontogram(@Valid @PathVariable Long id) {
        odontogramService.deleteOdontogram(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/save")
    public ResponseEntity<OdontogramModel> createOdontogram(@RequestBody OdontogramModel odontogram) {
        OdontogramModel savedOdontogram = odontogramService.saveOdontogram(odontogram);
        return new ResponseEntity<>(savedOdontogram, HttpStatus.CREATED);
    }
}
