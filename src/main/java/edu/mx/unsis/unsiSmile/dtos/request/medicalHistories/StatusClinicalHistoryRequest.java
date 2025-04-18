package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusClinicalHistoryRequest {

    @NotNull(message = ResponseMessages.STATUS_NULL)
    private String status;

    private String message;

    @NotNull(message = ResponseMessages.PATIENT_CLINICAL_HISTORY_ID_NULL)
    private Long idPatientClinicalHistory;

    @NotNull(message = ResponseMessages.FORM_SECTION_ID_NULL)
    private Long idSection;
}