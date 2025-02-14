package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatusClinicalHistoryResponse {
    private Long idStatusClinicalHistory;
    private String status;
    private String message;
    private Long idPatientClinicalHistory;
}
