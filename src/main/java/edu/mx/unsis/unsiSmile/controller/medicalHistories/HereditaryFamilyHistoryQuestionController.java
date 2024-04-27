package edu.mx.unsis.unsiSmile.controller.medicalHistories;

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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HereditaryFamilyHistoryQuestionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HereditaryFamilyHistoryQuestionResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.HereditaryFamilyHistoryQuestionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/hereditary-family-history-questions")
public class HereditaryFamilyHistoryQuestionController {

    private final HereditaryFamilyHistoryQuestionService familyHistoryQuestionService;

    public HereditaryFamilyHistoryQuestionController(HereditaryFamilyHistoryQuestionService familyHistoryQuestionService) {
        this.familyHistoryQuestionService = familyHistoryQuestionService;
    }

    @PostMapping
    public ResponseEntity<HereditaryFamilyHistoryQuestionResponse> createFamilyHistoryQuestion(@Valid @RequestBody HereditaryFamilyHistoryQuestionRequest familyHistoryQuestionRequest) {
        HereditaryFamilyHistoryQuestionResponse createdFamilyHistoryQuestion = familyHistoryQuestionService.createFamilyHistoryQuestion(familyHistoryQuestionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFamilyHistoryQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HereditaryFamilyHistoryQuestionResponse> getFamilyHistoryQuestionById(@Valid @PathVariable Long id) {
        HereditaryFamilyHistoryQuestionResponse familyHistoryQuestionResponse = familyHistoryQuestionService.getFamilyHistoryQuestionById(id);
        return ResponseEntity.ok(familyHistoryQuestionResponse);
    }

    @GetMapping
    public ResponseEntity<List<HereditaryFamilyHistoryQuestionResponse>> getAllFamilyHistoryQuestions() {
        List<HereditaryFamilyHistoryQuestionResponse> allFamilyHistoryQuestions = familyHistoryQuestionService.getAllFamilyHistoryQuestions();
        return ResponseEntity.ok(allFamilyHistoryQuestions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HereditaryFamilyHistoryQuestionResponse> updateFamilyHistoryQuestion(@Valid @PathVariable Long id,
            @Valid @RequestBody HereditaryFamilyHistoryQuestionRequest updatedFamilyHistoryQuestionRequest) {
        HereditaryFamilyHistoryQuestionResponse updatedFamilyHistoryQuestion = familyHistoryQuestionService.updateFamilyHistoryQuestion(id, updatedFamilyHistoryQuestionRequest);
        return ResponseEntity.ok(updatedFamilyHistoryQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFamilyHistoryQuestionById(@Valid @PathVariable Long id) {
        familyHistoryQuestionService.deleteFamilyHistoryQuestionById(id);
        return ResponseEntity.noContent().build();
    }
}
