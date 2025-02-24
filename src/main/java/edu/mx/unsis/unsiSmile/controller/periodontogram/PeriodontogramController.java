package edu.mx.unsis.unsiSmile.controller.periodontogram;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.request.periodontogram.PeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.periodontograms.PeriodontogramResponse;
import edu.mx.unsis.unsiSmile.service.periodontogram.PeriodontogramService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("unsismile/api/v1/medical-records/periodontograms")
@AllArgsConstructor
public class PeriodontogramController {

    private final PeriodontogramService periodontogramService;

    @GetMapping
    public List<PeriodontogramResponse> getAllPeriodontograms() {
        return periodontogramService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodontogramResponse> getPeriodontogramById(@PathVariable Long id) {
        Optional<PeriodontogramResponse> periodontogram = periodontogramService.findById(id);
        return periodontogram.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PeriodontogramResponse createPeriodontogram(@RequestBody PeriodontogramRequest periodontogramRequest) {
        return periodontogramService.save(periodontogramRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodontogramResponse> updatePeriodontogram(@PathVariable Long id, @RequestBody PeriodontogramRequest periodontogramRequest) {
        PeriodontogramResponse updatedPeriodontogram = periodontogramService.update(id, periodontogramRequest);
        return ResponseEntity.ok(updatedPeriodontogram);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriodontogram(@PathVariable Long id) {
        periodontogramService.delete(id);
        return ResponseEntity.noContent().build();
    }
}