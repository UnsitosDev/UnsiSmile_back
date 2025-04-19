package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewStatusResponse {
    private Long idReviewStatus;
    private String status;
    private String message;
    private Long idPatientClinicalHistory;
    private Long idSection;
    private Long idProfessorClinicalArea;
}
