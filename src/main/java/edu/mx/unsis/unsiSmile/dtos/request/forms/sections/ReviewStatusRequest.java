package edu.mx.unsis.unsiSmile.dtos.request.forms.sections;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewStatusRequest {
    @NotNull(message = ResponseMessages.ID_REVIEW_STATUS_NULL)
    private Long idReviewStatus;

    @NotNull(message = ResponseMessages.STATUS_NULL)
    private ReviewStatus status;

    private String message;
}