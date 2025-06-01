package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentReviewRequest {
    @NotNull(message = ResponseMessages.PROFESSOR_CLINICAL_AREA_ID_CANNOT_BE_NULL)
    private Long professorClinicalAreaId;

    private String comment;
}