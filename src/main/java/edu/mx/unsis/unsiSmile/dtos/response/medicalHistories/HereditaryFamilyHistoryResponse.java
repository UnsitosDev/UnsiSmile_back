package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HereditaryFamilyHistoryResponse {
    private Long idFamilyHistory;
    private HereditaryFamilyHistoryQuestionResponse question;
    private String mainResponse;
    private String responseDetail;
}
