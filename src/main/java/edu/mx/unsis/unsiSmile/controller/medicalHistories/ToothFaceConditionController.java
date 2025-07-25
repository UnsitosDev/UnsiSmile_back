package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothFaceConditionResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ToothFaceConditionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Condiciones de cara de diente.")
@RestController
@AllArgsConstructor
@RequestMapping("/unsismile/api/v1/medical-records/tooth-face-conditions")
public class ToothFaceConditionController {

    private final ToothFaceConditionService toothFaceConditionService;

    @PostMapping
    public ResponseEntity<ToothFaceConditionResponse> createToothCondition(@Valid @RequestBody ToothFaceConditionRequest request) {
        ToothFaceConditionResponse createdToothFaceConditionResponse = toothFaceConditionService.createToothCondition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdToothFaceConditionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToothFaceConditionResponse> getToothFaceConditionById(@Valid @PathVariable Long id) {
        ToothFaceConditionResponse toothFaceConditionResponse = toothFaceConditionService.getToothConditionById(id);
        return ResponseEntity.ok(toothFaceConditionResponse);
    }

    @GetMapping
    public ResponseEntity<List<ToothFaceConditionResponse>> getOdontogramConditions() {
        List<ToothFaceConditionResponse> conditions = toothFaceConditionService.getAllToothConditions(Constants.ODONTOGRAM);
        return ResponseEntity.ok(conditions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToothFaceConditionResponse> updateToothCondition(@Valid @PathVariable Long id,
                                                                        @Valid @RequestBody ToothFaceConditionRequest updateRequest) {
        ToothFaceConditionResponse updatedToothFaceConditionResponse = toothFaceConditionService.updateToothCondition(id, updateRequest);
        return ResponseEntity.ok(updatedToothFaceConditionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToothCondition(@Valid @PathVariable Long id) {
        toothFaceConditionService.deleteToothCondition(id);
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/by-description")
    // public ResponseEntity<ToothFaceConditionResponse> getToothConditionByDescription(@RequestParam(value = "description") String description) {
    //     Optional<ToothConditionModel> toothConditionModel = toothFaceConditionService.findByDescription(description);
    //     if (toothConditionModel.isPresent()) {
    //         return ResponseEntity.ok(toothFaceConditionService.toothConditionMapper.toDto(toothConditionModel.get()));
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @GetMapping("/prophylaxis")
    public ResponseEntity<List<ToothFaceConditionResponse>> getProphylaxisConditions() {
        List<ToothFaceConditionResponse> conditions = toothFaceConditionService.getAllToothConditions(Constants.PROPHYLAXIS);
        return ResponseEntity.ok(conditions);
    }

    @GetMapping("/fluorosis")
    public ResponseEntity<List<ToothFaceConditionResponse>> getFluorosisConditions() {
        List<ToothFaceConditionResponse> conditions = toothFaceConditionService.getAllToothConditions(Constants.FLUOROSIS);
        return ResponseEntity.ok(conditions);
    }
}
