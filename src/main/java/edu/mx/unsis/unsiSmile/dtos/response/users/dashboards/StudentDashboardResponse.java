package edu.mx.unsis.unsiSmile.dtos.response.users.dashboards;

import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentCountResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDashboardResponse {
    private Long totalPatients;
    private Long patientsWithDisability;
    private Long patientsRegisteredLastMonth;
    private Map<String, Long> patientsByNationality;
    private Long patientsUnder18;
    private Long patientsBetween18And60;
    private Long patientsOver60;
    private TreatmentCountResponse treatments;
    private Long rejectedTreatments;
    private Long progressingTreatments;
    private Long inReviewTreatments;
}