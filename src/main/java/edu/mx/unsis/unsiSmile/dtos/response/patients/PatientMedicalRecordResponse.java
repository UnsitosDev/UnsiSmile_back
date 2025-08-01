package edu.mx.unsis.unsiSmile.dtos.response.patients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientMedicalRecordResponse {
    private Long id;
    private String medicalRecordName;

    @Builder.Default
    private Long patientMedicalRecordId = 0L;
    private String patientId;
}