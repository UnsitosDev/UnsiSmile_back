package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramRequest {
    private Long idOdontogram;

    private LocalDate creationDate;
    @NotNull(message = "The ToothConditionAssignmentDTO can't be null")
    private List<ToothConditionAssignmentRequest> toothConditionAssignments;
    @NotNull(message = "The ToothFaceConditionDTO can't be null")
    private List<ToothFaceConditionRequest> toothFaceConditions;private String description;

}
