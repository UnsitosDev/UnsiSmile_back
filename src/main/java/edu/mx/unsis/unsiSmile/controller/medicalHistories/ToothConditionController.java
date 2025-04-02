package edu.mx.unsis.unsiSmile.controller.medicalHistories;

import java.util.List;

import edu.mx.unsis.unsiSmile.common.Constants;
import lombok.AllArgsConstructor;
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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothConditionResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ToothConditionService;
import jakarta.validation.Valid;

@RestController
@AllArgsConstructor         
@RequestMapping("/unsismile/api/v1/medical-histories/tooth-conditions")
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
    public ResponseEntity<List<ToothConditionResponse>> getProfilaxisConditions() {
        List<ToothConditionResponse> conditions = toothConditionService.getAllToothConditions(Constants.PROPHYLAXIS);
        return ResponseEntity.ok(conditions);
    }

    @GetMapping("/fluorosis")
    public ResponseEntity<List<ToothConditionResponse>> getFluorosisConditions() {
        List<ToothConditionResponse> conditions = toothConditionService.getAllToothConditions(Constants.FLUOROSIS);
        return ResponseEntity.ok(conditions);
    }
}
