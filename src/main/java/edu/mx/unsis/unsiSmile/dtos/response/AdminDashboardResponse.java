package edu.mx.unsis.unsiSmile.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDashboardResponse {
    private Long totalPatients;
    private Long patientsWithDisability;
    private Long patientsRegisteredLastMonth;
    private Map<String, Long> patientsByNationality;
    private Long totalStudents;
    private Long studentsRegisteredLastMonth;
    private Long totalProfessors;
    private TreatmentCountResponse treatments;
    private Long rejectedTreatments;
    private Long progressingTreatments;
    private Long inReviewTreatments;
}