package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

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
public class TreatmentDetailResponse {

    private Long idTreatmentDetail;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private PatientResponse patient;
    private ProfessorResponse approvalProfessor;
    private ProfessorResponse reviewProfessor;
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
        private Long medicalRecordNumber;
        private Long idPatientMedicalRecord;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfessorResponse {
        private Long idProfessorClinicalArea;
        private String professorName;
        private String comments;
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