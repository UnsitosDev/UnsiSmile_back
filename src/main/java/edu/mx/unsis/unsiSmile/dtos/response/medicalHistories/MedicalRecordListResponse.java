package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordListResponse {
    private PatientMedicalRecordResponse patientMedicalRecord;
    private LocalDate appointmentDate;
    private TreatmentDetailResponse treatmentDetail;
}
