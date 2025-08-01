package edu.mx.unsis.unsiSmile.dtos.response.patients;

import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordListResponse {
    private PatientResponse patient;
    private Page<MedicalRecordResponse> page;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientResponse {
        private String id;
        private String name;
        private Long medicalRecordNumber;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicalRecordResponse {
        private Long id;
        private String medicalRecordName;
        @Builder.Default
        private Long patientMedicalRecordId = 0L;

        private LocalDate appointmentDate;

        private TreatmentDetailResponse treatmentDetail;
    }
}