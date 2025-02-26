package edu.mx.unsis.unsiSmile.dtos.response.periodontograms;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
