package edu.mx.unsis.unsiSmile.dtos.request.periodontogram;

import java.util.List;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class PeriodontogramRequest {
    @NotEmpty(message = "Patient ID cannot be empty")
    private String patientId;

    @NotNull(message = "Plaque index cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Plaque index must be at least 0.0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Plaque index must be at most 100.0")
    private Double plaqueIndex;

    @NotNull(message = "Bleeding index cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Bleeding index must be at least 0.0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Bleeding index must be at most 100.0")
    private Double bleedingIndex;

    @Size(max = 500, message = "Notes cannot be longer than 500 characters")
    private String notes;

    @NotNull(message = "Tooth evaluations cannot be null")
    @Size(min = 1, message = "There must be at least one tooth evaluation")
    private List<ToothEvaluationRequest> toothEvaluations;

    @NotNull(message = "Form section ID cannot be null")
    private PeriodontogramsFormSection formSection;
}
