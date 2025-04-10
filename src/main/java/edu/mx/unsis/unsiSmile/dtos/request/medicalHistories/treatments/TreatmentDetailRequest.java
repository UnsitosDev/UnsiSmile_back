package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailRequest {

    private Long idTreatmentDetail;

    @NotNull(message = ResponseMessages.PATIENT_ID_CANNOT_BE_NULL)
    @NotBlank(message = ResponseMessages.PATIENT_ID_CANNOT_BE_EMPTY)
    private String patientId;

    @NotNull(message = ResponseMessages.TREATMENT_ID_CANNOT_BE_NULL)
    @Positive(message = ResponseMessages.POSITIVE_TREATMENT_ID)
    private Long treatmentId;

    @NotNull(message = ResponseMessages.TREATMENT_SCOPE_ID_CANNOT_BE_NULL)
    @Positive(message = ResponseMessages.POSITIVE_TREATMENT_SCOPE_ID)
    private Long treatmentScopeId;

    @NotNull(message = ResponseMessages.TREATMENT_DATE_CANNOT_BE_NULL)
    private LocalDateTime treatmentDate;

    private String professorId;

    @Size(max = 50, message = "Status must be maximum 50 characters")
    private String status;
}