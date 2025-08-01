package edu.mx.unsis.unsiSmile.dtos.request.treatments;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailToothRequest {

    @NotNull(message = "Treatment detail ID cannot be null")
    private Long idTreatmentDetail;

    @NotEmpty(message = "Tooth list cannot be empty")
    private List<String> idTeeth;
}