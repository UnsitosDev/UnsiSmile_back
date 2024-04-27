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

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialExamRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialExamResponse;
import edu.mx.unsis.unsiSmile.service.medicalHistories.FacialExamService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/medical-histories/facial-exams")
public class FacialExamController {

    private final FacialExamService facialExamService;

    public FacialExamController(FacialExamService facialExamService) {
        this.facialExamService = facialExamService;
    }

    @PostMapping
    public ResponseEntity<FacialExamResponse> createFacialExam(@Valid @RequestBody FacialExamRequest facialExamRequest) {
        FacialExamResponse createdFacialExam = facialExamService.createFacialExam(facialExamRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFacialExam);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacialExamResponse> getFacialExamById(@Valid @PathVariable Long id) {
        FacialExamResponse facialExamResponse = facialExamService.getFacialExamById(id);
        return ResponseEntity.ok(facialExamResponse);
    }

    @GetMapping
    public ResponseEntity<List<FacialExamResponse>> getAllFacialExams() {
        List<FacialExamResponse> allFacialExams = facialExamService.getAllFacialExams();
        return ResponseEntity.ok(allFacialExams);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacialExamResponse> updateFacialExam(@Valid @PathVariable Long id,
            @Valid @RequestBody FacialExamRequest updatedFacialExamRequest) {
        FacialExamResponse updatedFacialExam = facialExamService.updateFacialExam(id, updatedFacialExamRequest);
        return ResponseEntity.ok(updatedFacialExam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFacialExamById(@Valid @PathVariable Long id) {
        facialExamService.deleteFacialExamById(id);
        return ResponseEntity.noContent().build();
    }
}
