package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HereditaryFamilyHistoryRequest {

    private Long idFamilyHistory;

    @NotNull(message = "The familyHistoryQuestion can't be null")
    private HereditaryFamilyHistoryQuestionRequest familyHistoryQuestion;
    @NotBlank(message = "The mainResponse can't be blank")
    private String mainResponse;
    @NotBlank(message = "The responseDetail can't be blank")
    private String responseDetail;
}
