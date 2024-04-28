package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenAnswerPathologicalRequest {
    private Long idOpenAnswerNonPathological;
    private OpenQuestionPathologicalAntecedentsRequest openQuestion;
    private MedicalHistoryRequest medicalHistory;
    private String answer;
}
