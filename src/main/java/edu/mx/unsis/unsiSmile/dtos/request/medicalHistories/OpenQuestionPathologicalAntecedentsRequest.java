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
public class OpenQuestionPathologicalAntecedentsRequest {
    private Long idOpenQuestion;

    @NotNull(message = "The question can't be null")
    private String question;
}
