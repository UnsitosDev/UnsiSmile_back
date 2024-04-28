package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClosedAnswerPathologicalRequest {
    private Long idClosedAnswerNonPathological;
    private Long closedQuestionId;
    private Long medicalHistoryId;
    private String answer;
}
