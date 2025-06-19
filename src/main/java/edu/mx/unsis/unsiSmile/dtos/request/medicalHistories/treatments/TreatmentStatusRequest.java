package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TreatmentStatusRequest {

    @NotNull(message = ResponseMessages.TREATMENT_DETAIL_ID_CANNOT_BE_NULL)
    private Long treatmentDetailId;

    @NotNull(message = ResponseMessages.PROFESSOR_CLINICAL_AREA_ID_CANNOT_BE_NULL)
    private Long professorClinicalAreaId;

    private String comment;

    private ReviewStatus status;
}