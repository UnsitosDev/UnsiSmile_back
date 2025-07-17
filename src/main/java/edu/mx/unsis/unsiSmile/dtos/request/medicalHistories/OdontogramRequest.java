package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "The patient id can't be blank")
    private String idPatient;
    @NotNull(message = "The id patient clinical history cannot be null")
    private Long idPatientClinicalHistory;
    private String observations;

}
    