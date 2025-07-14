package edu.mx.unsis.unsiSmile.dtos.request.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DigitizerPatientRequest {

    @NotNull(message = ResponseMessages.PATIENT_ID_CANNOT_BE_NULL)
    @NotBlank(message = ResponseMessages.PATIENT_ID_CANNOT_BE_EMPTY)
    private String patientId;

    @NotNull(message = ResponseMessages.DIGITIZER_PATIENT_ID_CANNOT_BE_NULL)
    private Long medicalRecordDigitizerId;
}