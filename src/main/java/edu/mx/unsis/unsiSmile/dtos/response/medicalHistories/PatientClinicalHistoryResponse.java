package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientClinicalHistoryResponse {

    private Long id;

    private String clinicalHistoryName;

    @Builder.Default
    private Long patientClinicalHistoryId = 0L;

    @Builder.Default
    private Long patientId = 0L;
}
