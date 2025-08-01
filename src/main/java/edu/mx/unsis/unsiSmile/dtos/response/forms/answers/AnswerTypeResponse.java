package edu.mx.unsis.unsiSmile.dtos.response.forms.answers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerTypeResponse {
    private Long idAnswerType;
    private String description;
}