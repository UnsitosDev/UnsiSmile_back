package edu.mx.unsis.unsiSmile.dtos.request.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentStatusUpdateRequest {
    @NotNull(message = ResponseMessages.STATUS_NULL)
    private ReviewStatus status;

    @Size(max = 255, message = ResponseMessages.COMMENT_MAX_LENGTH)
    private String comments;
}