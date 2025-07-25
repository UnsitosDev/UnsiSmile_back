package edu.mx.unsis.unsiSmile.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicalSupervisorDashboardResponse {
    private Integer totalGroups;
    private Long totalStudents;

    private Long medicalRecordsInReview;
    private Long medicalRecordsRejected;
    private Long medicalRecordsAccepted;

    private Long treatmentsCompleted;
}