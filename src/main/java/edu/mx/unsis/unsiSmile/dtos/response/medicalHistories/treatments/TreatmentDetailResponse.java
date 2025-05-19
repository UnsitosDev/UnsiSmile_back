package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailResponse {

    private Long idTreatmentDetail;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    private PatientResponse patient;
    private ProfessorResponse professor;
    private StudentResponse student;

    private TreatmentResponse treatment;
    private List<TreatmentDetailToothResponse> teeth;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientResponse {
        private String id;
        private String name;
        private Long idPatientMedicalRecord;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfessorResponse {
        private String id;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentResponse {
        private String id;
        private String name;
        private Long idGroup;
        private String group;
    }
}