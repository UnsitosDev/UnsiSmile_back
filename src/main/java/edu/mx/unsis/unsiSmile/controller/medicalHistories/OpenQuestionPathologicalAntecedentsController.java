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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OpenQuestionPathologicalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OpenQuestionPathologicalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.OpenQuestionsPathologicalAntecedentsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/open-question-pathological-antecedents")
public class OpenQuestionPathologicalAntecedentsController {

    private final OpenQuestionsPathologicalAntecedentsService openQuestionsPathologicalAntecedentsService;

    public OpenQuestionPathologicalAntecedentsController(OpenQuestionsPathologicalAntecedentsService openQuestionsPathologicalAntecedentsService) {
        this.openQuestionsPathologicalAntecedentsService = openQuestionsPathologicalAntecedentsService;
    }

    @PostMapping
    public ResponseEntity<OpenQuestionPathologicalAntecedentsResponse> createOpenQuestion(
            @Valid @RequestBody OpenQuestionPathologicalAntecedentsRequest request) {
        OpenQuestionPathologicalAntecedentsResponse createdOpenQuestionResponse = openQuestionsPathologicalAntecedentsService
                .createOpenQuestion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOpenQuestionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpenQuestionPathologicalAntecedentsResponse> getOpenQuestionById(
            @Valid @PathVariable Long id) {
        OpenQuestionPathologicalAntecedentsResponse openQuestionResponse = openQuestionsPathologicalAntecedentsService
                .getOpenQuestionById(id);
        return ResponseEntity.ok(openQuestionResponse);
    }

    @GetMapping
    public ResponseEntity<List<OpenQuestionPathologicalAntecedentsResponse>> getAllOpenQuestions() {
        List<OpenQuestionPathologicalAntecedentsResponse> allOpenQuestions = openQuestionsPathologicalAntecedentsService
                .getAllOpenQuestions();
        return ResponseEntity.ok(allOpenQuestions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpenQuestionPathologicalAntecedentsResponse> updateOpenQuestion(
            @Valid @PathVariable Long id,
            @Valid @RequestBody OpenQuestionPathologicalAntecedentsRequest updateRequest) {
        OpenQuestionPathologicalAntecedentsResponse updatedOpenQuestionResponse = openQuestionsPathologicalAntecedentsService
                .updateOpenQuestion(id, updateRequest);
        return ResponseEntity.ok(updatedOpenQuestionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOpenQuestion(@Valid @PathVariable Long id) {
        openQuestionsPathologicalAntecedentsService.deleteOpenQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
