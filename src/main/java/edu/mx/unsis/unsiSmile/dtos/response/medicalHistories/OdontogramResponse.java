package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramResponse {
    private Long idOdontogram;
    private List<ToothResponse> adultArcade;
    private List<ToothResponse> childArcade;
}
