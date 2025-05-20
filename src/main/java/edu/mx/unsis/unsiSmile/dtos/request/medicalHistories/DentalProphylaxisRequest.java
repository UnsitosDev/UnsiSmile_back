package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DentalProphylaxisRequest {
    @NotNull(message = ResponseMessages.REQUEST_CANNOT_BE_NULL)
    private List<ToothRequest> theetProphylaxis;

    @NotNull(message = ResponseMessages.PATIENT_ID_CANNOT_BE_NULL)
    private String idPatient;

    @NotNull(message = ResponseMessages.QUESTION_ID_CANNOT_BE_NULL)
    private Long idQuestion;

    @NotNull(message = ResponseMessages.CLINICAL_HISTORY_ID_CANNOT_BE_NULL)
    private Long idPatientClinicalHistory;

    @NotNull(message = ResponseMessages.FORM_SECTION_ID_CANNOT_BE_NULL)
    private Long idFormSection;

    @DecimalMin(value = "0.00", message = ResponseMessages.PERCENTAGE_MIN_VALUE_ERROR)
    @DecimalMax(value = "100.00", message = ResponseMessages.PERCENTAGE_MAX_VALUE_ERROR)
    private BigDecimal percentage;
}