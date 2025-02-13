package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusClinicalHistoryRequest {
    private String status;

    private String message;
    
    private Long idPatientClinicalHistory;
}