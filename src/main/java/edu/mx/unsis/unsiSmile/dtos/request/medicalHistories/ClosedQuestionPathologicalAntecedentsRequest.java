package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClosedQuestionPathologicalAntecedentsRequest {
    private Long idClosedQuestion;
    
    @NotNull(message = "The question facialFront can't be null")
    private String question;
}
