package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailResponse {

    private Long idTreatmentDetail;
    private Long patientClinicalHistoryId;
    private String patientId;
    private String patientName;
    private TreatmentResponse treatment;
    List<TreatmentDetailToothResponse> teeth;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long studentGroupId;
    private String professorId;
    private String professorName;
    private String status;
}