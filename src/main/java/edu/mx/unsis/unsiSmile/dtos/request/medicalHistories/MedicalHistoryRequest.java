package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalHistoryRequest {
    private Long idMedicalHistory;

    @NotNull(message = "The patient can't be null")
    private Long patient;

    private FacialExamRequest facialExam;

    private NonPathologicalPersonalAntecedentsRequest nonPathologicalPersonalAntecedents;

    private OdontogramRequest initialOdontogram;

    private OdontogramRequest finalOdontogram;
}
