package edu.mx.unsis.unsiSmile.dtos.response.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentReportResponse {
    private String studentName;
    private List<GroupedTreatmentResponse> treatments;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupedTreatmentResponse {
        private String treatmentName;
        private List<TreatmentReportDetailResponse> details;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreatmentReportDetailResponse {
        private LocalDate treatmentDate;
        private String toothId;
        private String patientName;
        private String medicalRecordNumber;
        private String professorName;
    }
}