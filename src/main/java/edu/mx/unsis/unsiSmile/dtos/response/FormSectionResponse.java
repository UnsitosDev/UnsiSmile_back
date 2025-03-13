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
public class FormSectionResponse {

    private Long idFormSection;

    private String formName;

    private Boolean isAnswered;

    private Boolean requiresReview;

    private String status;

    private List<FormSectionResponse> subSections;

    private List<QuestionResponse> questions;
}
