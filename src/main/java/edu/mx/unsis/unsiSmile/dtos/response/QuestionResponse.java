package edu.mx.unsis.unsiSmile.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {

    private Long idQuestion;

    private String questionText;

    private String placeholder;

    private Boolean required;

    private Long order;

    private AnswerTypeResponse answerType;

    private CatalogResponse catalog;

    private AnswerResponse answer;

    private List<ValidationResponse> questionValidations;
}
