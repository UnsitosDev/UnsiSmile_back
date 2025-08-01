package edu.mx.unsis.unsiSmile.controller.medicalrecords.teeth;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothConditionResponse;
import edu.mx.unsis.unsiSmile.service.medicalrecords.teeth.ToothConditionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Condicines de diente")
@RestController
@AllArgsConstructor         
@RequestMapping("/unsismile/api/v1/medical-records/tooth-conditions")
public class ToothConditionController {

    private final ToothConditionService toothConditionService;

    @PostMapping
    public ResponseEntity<ToothConditionResponse> createToothCondition(@Valid @RequestBody ToothConditionRequest request) {
        ToothConditionResponse createdToothConditionResponse = toothConditionService.createToothCondition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToothConditionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToothConditionResponse> getToothConditionById(@Valid @PathVariable Long id) {
        ToothConditionResponse toothConditionResponse = toothConditionService.getToothConditionById(id);
        return ResponseEntity.ok(toothConditionResponse);
    }

    @GetMapping()
    public ResponseEntity<List<ToothConditionResponse>> getOdontogramConditions() {
        List<ToothConditionResponse> conditions = toothConditionService.getAllToothConditions(Constants.ODONTOGRAM);
        return ResponseEntity.ok(conditions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToothConditionResponse> updateToothCondition(@Valid @PathVariable Long id,
                                                                        @Valid @RequestBody ToothConditionRequest updateRequest) {
        ToothConditionResponse updatedToothConditionResponse = toothConditionService.updateToothCondition(id, updateRequest);
        return ResponseEntity.ok(updatedToothConditionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToothCondition(@Valid @PathVariable Long id) {
        toothConditionService.deleteToothCondition(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/by-description")
    // public ResponseEntity<ToothConditionResponse> getToothConditionByDescription(@RequestParam(value = "description") String description) {
    //     Optional<ToothConditionModel> toothConditionModel = toothConditionService.findByDescription(description);
    //     if (toothConditionModel.isPresent()) {
    //         return ResponseEntity.ok(toothConditionService.toothConditionMapper.toDto(toothConditionModel.get()));
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @GetMapping("/prophylaxis")
    public ResponseEntity<List<ToothConditionResponse>> getProphylaxisConditions() {
        List<ToothConditionResponse> conditions = toothConditionService.getAllToothConditions(Constants.PROPHYLAXIS);
        return ResponseEntity.ok(conditions);
    }

    @GetMapping("/fluorosis")
    public ResponseEntity<List<ToothConditionResponse>> getFluorosisConditions() {
        List<ToothConditionResponse> conditions = toothConditionService.getAllToothConditions(Constants.FLUOROSIS);
        return ResponseEntity.ok(conditions);
    }
}