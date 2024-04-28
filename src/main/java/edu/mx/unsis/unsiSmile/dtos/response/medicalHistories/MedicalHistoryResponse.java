package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalHistoryResponse {
    private Long idMedicalHistory;
    private Long patientId;
    private Long facialExamId;
    private Long familyHistoryId;
    private Long nonPathologicalPersonalAntecedentsId;
    private Long initialOdontogramId;
    private Long finalOdontogramId;
}
