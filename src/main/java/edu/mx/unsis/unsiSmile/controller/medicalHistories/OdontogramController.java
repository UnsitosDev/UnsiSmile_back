package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.OdontogramService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/odontograms")
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

    @PostMapping("")
    public ResponseEntity<Void> createOdontogram(@RequestBody OdontogramRequest odontogramDTO) {
        odontogramService.saveOdontogram(odontogramDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/details")
    public OdontogramResponse getOdontogramDetails(@PathVariable Long id) {
        return odontogramService.getOdontogramDetails(id);
    }
}
