package edu.mx.unsis.unsiSmile.dtos.response.periodontograms;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeriodontogramResponse {
    private Long id;
    private String patientId;
    private Double plaqueIndex;
    private Double bleedingIndex;
    private LocalDateTime evaluationDate;
    private String notes;
    private List<ToothEvaluationResponse> toothEvaluations;
}