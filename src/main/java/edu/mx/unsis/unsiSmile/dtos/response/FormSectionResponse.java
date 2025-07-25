package edu.mx.unsis.unsiSmile.dtos.response;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ReviewSectionResponse;
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

    private String idFormSection;

    private String formName;

    private Boolean isAnswered;

    private Boolean requiresReview;

    private ReviewSectionResponse reviewStatus;

    private List<FormSectionResponse> subSections;

    private List<QuestionResponse> questions;

    private Long sectionOrder;
}
