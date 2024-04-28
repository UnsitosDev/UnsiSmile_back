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
    @NotNull(message = "The facialExam can't be null")
    private FacialExamRequest facialExam;
    @NotNull(message = "The nonPathologicalPersonalAntecedents can't be null")
    private NonPathologicalPersonalAntecedentsRequest nonPathologicalPersonalAntecedents;
    @NotNull(message = "The initialOdontogram can't be null")
    private OdontogramRequest initialOdontogram;

    private Long finalOdontogram;
}
