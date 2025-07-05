package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
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

    @NotNull(message = ResponseMessages.TREATMENT_START_DATE_CANNOT_BE_NULL)
    private LocalDateTime startDate;

    @NotNull(message = ResponseMessages.TREATMENT_END_DATE_CANNOT_BE_NULL)
    private LocalDateTime endDate;

    @NotNull(message = ResponseMessages.PROFESSOR_CLINICAL_AREA_ID_CANNOT_BE_NULL)
    private Long professorClinicalAreaId;

    TreatmentDetailToothRequest treatmentDetailToothRequest;
}