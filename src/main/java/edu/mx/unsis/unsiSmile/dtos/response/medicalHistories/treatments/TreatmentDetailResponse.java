package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailResponse {

    private Long idTreatmentDetail;
    private Long patientClinicalHistoryId;
    private String patientName;
    private TreatmentResponse treatment;
    private TreatmentScopeResponse treatmentScope;
    private LocalDateTime treatmentDate;
    private Long studentGroupId;
    private String professorId;
    private String professorName;
    private String status;
}