package edu.mx.unsis.unsiSmile.dtos.response.forms.sections;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ReviewStatusResponse extends ReviewSectionResponse{
    private Long idPatientMedicalRecord;
    private String idSection;
    private Long idProfessorClinicalArea;
    private Long idTreatmentDetail;
    private String studentName;
    private PatientResp patient;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientResp {
        private String id;
        private String name;
        private String curp;
        private Long medicalRecordNumber;
    }
}
