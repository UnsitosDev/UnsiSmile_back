package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramResponse {
    private Long idOdontogram;
    private List<ToothResponse> adultArcade;
    private List<ToothResponse> childArcade;
    private String observations;
    private Long idTreatmentDetail;
}
