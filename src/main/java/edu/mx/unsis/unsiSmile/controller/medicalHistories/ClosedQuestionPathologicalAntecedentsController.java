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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClosedQuestionPathologicalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClosedQuestionPathologicalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.ClosedQuestionsPathologicalAntecedentsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/closed-question-pathological-antecedents")
public class ClosedQuestionPathologicalAntecedentsController {

    private final ClosedQuestionsPathologicalAntecedentsService closedQuestionsPathologicalAntecedentsService;

    public ClosedQuestionPathologicalAntecedentsController(ClosedQuestionsPathologicalAntecedentsService closedQuestionsPathologicalAntecedentsService) {
        this.closedQuestionsPathologicalAntecedentsService = closedQuestionsPathologicalAntecedentsService;
    }

    @PostMapping
    public ResponseEntity<ClosedQuestionPathologicalAntecedentsResponse> createClosedQuestion(
            @Valid @RequestBody ClosedQuestionPathologicalAntecedentsRequest request) {
        ClosedQuestionPathologicalAntecedentsResponse createdClosedQuestionResponse = closedQuestionsPathologicalAntecedentsService
                .createClosedQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClosedQuestionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClosedQuestionPathologicalAntecedentsResponse> getClosedQuestionById(
            @Valid @PathVariable Long id) {
        ClosedQuestionPathologicalAntecedentsResponse closedQuestionResponse = closedQuestionsPathologicalAntecedentsService
                .getClosedQuestionById(id);
        return ResponseEntity.ok(closedQuestionResponse);
    }

    @GetMapping
    public ResponseEntity<List<ClosedQuestionPathologicalAntecedentsResponse>> getAllClosedQuestions() {
        List<ClosedQuestionPathologicalAntecedentsResponse> allClosedQuestions = closedQuestionsPathologicalAntecedentsService
                .getAllClosedQuestions();
        return ResponseEntity.ok(allClosedQuestions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClosedQuestionPathologicalAntecedentsResponse> updateClosedQuestion(
            @Valid @PathVariable Long id,
            @Valid @RequestBody ClosedQuestionPathologicalAntecedentsRequest updateRequest) {
        ClosedQuestionPathologicalAntecedentsResponse updatedClosedQuestionResponse = closedQuestionsPathologicalAntecedentsService
                .updateClosedQuestion(id, updateRequest);
        return ResponseEntity.ok(updatedClosedQuestionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClosedQuestion(@Valid @PathVariable Long id) {
        closedQuestionsPathologicalAntecedentsService.deleteClosedQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
