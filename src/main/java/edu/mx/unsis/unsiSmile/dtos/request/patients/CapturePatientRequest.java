package edu.mx.unsis.unsiSmile.dtos.request.patients;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CapturePatientRequest extends PatientRequest{

    @Positive(message = ResponseMessages.MEDICAL_RECORD_NUMBER_MUST_BE_POSITIVE)
    @NotNull(message = ResponseMessages.NOT_NULL_MEDICAL_RECORD_NUMBER)
    private Long MedicalRecordNumber;
}