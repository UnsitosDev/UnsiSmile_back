package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentScopeResponse {
    private Long idScopeTreatment;
    private String name;
}