package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenAnswerPathologicalResponse {
    private Long idOpenAnswerNonPathological;
    private Long openQuestionId;
    private Long medicalHistoryId;
    private String answer;
}
