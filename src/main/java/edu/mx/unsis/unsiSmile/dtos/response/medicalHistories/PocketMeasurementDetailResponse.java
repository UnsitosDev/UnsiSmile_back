package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PocketMeasurementDetailResponse {
    private Long idPocketMeasurementDetail;
    private Float measurement;
    private ToothRegionPeriodontogramResponse toothRegionsPeriodontogram;
    private DentalCodeResponse dentalCode;
    private RegionMeasurementPocketsResponse regionsMeasurementPockets;
    private PeriodontogramResponse periodontogram;
}

