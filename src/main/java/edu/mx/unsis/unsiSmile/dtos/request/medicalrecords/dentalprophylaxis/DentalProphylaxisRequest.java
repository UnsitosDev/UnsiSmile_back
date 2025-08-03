package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothRequest;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = ResponseMessages.NOT_NULL_PATIENT_MEDICAL_RECORD_ID)
    @Positive(message = ResponseMessages.POSITIVE_ID_MESSAGE)
    private Long idPatientMedicalRecord;

    @DecimalMin(value = "0.00", message = ResponseMessages.PERCENTAGE_MIN_VALUE_ERROR)
    @DecimalMax(value = "100.00", message = ResponseMessages.PERCENTAGE_MAX_VALUE_ERROR)
    private BigDecimal percentage;
}