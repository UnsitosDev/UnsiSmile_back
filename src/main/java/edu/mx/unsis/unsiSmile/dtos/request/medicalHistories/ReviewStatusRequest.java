package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewStatusRequest {
    @NotNull(message = ResponseMessages.ID_REVIEW_STATUS_NULL)
    private Long idReviewStatus;

    @NotNull(message = ResponseMessages.STATUS_NULL)
    private String status;

    private String message;
}