package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothDetailResponse {
    private Long idToothDetail;
    private DentalCodeResponse dentalCode;
    private ToothConditionResponse toothCondition;
    private ToothRegionResponse toothRegion;
    private OdontogramResponse odontogram;
}
