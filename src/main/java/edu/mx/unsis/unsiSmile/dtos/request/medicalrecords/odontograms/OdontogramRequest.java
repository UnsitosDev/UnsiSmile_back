package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.odontograms;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramRequest {

    @NotNull(message = "The request can't be null")
    private List<ToothRequest> teeth;

    @NotNull(message = "The id patient clinical history cannot be null")
    private String idPatient;

    private String observations;
}