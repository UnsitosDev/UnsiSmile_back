package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentResponse {

    private Long idTreatment;
    private String name;
    private TreatmentScopeResponse treatmentScope;
    private BigDecimal cost;
    private Long clinicalHistoryCatalogId;
    private String clinicalHistoryCatalogName;
}