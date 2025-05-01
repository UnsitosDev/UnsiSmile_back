package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramRequest {
    @NotNull(message = "The request can't be null")
    private List<ToothRequest> teeth;
    @NotNull(message = "The patient id can't be null")
    private String idPatient;
    @NotNull(message = "The id question cannot be null")
    private Long idQuestion;
    @NotNull(message = "The id of clinical history of cannot be null")
    private Long idPatientClinicalHistory;
    @NotNull(message = "The id of form section cannot be null")
    private Long idFormSection;

    private String observations;

}
