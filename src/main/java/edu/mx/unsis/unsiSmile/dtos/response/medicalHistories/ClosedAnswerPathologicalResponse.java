package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClosedAnswerPathologicalResponse {
    private Long idClosedAnswerNonPathological;
    private ClosedQuestionPathologicalAntecedentsResponse closedQuestion;
    private MedicalHistoryResponse medicalHistory;
    private String answer;
}
