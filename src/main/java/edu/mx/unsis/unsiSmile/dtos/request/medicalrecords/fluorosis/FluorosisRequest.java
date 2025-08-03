package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FluorosisRequest {
    @NotNull(message = ResponseMessages.REQUEST_CANNOT_BE_NULL)
    private List<ToothRequest> theetFluorosis;

    @NotNull(message = ResponseMessages.NOT_NULL_PATIENT_MEDICAL_RECORD_ID)
    @Positive(message = ResponseMessages.POSITIVE_ID_MESSAGE)
    private Long idPatientMedicalRecord;
}