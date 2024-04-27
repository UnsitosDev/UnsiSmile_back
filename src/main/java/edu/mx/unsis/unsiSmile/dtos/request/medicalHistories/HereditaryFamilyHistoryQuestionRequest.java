package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HereditaryFamilyHistoryQuestionRequest {
    
    private Long idQuestion;

    @NotBlank(message = "The field question can't be blank")
    private String question;
}
