package edu.mx.unsis.unsiSmile.dtos.response.dashboards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicalSupervisorDashboardResponse {

    private Long treatmentsAwaitingApproval;
    private Long treatmentsApproved;
    private Long treatmentsNotApproved;

    private Long treatmentsInProgress;
    private Long treatmentsInReview;
    private Long treatmentsRejected;

    private Long treatmentsCompleted;

    private Long medicalRecordsInReview;
    private Long medicalRecordsRejected;
    private Long medicalRecordsAccepted;
}