package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PocketMeasurementDetailRequest {
    private Long idPocketMeasurementDetail;
    
    @NotNull(message = "measurement cannot be null")
    private Float measurement;
    @NotNull(message = "toothRegionsPeriodontogram cannot be null")
    private ToothRegionPeriodontogramRequest toothRegionsPeriodontogram;
    @NotNull(message = "dentalCode cannot be null")
    private DentalCodeRequest dentalCode;
    @NotNull(message = "regionsMeasurementPockets cannot be null")
    private RegionMeasurementPocketsRequest regionsMeasurementPockets;
    @NotNull(message = "periodontogram cannot be null")
    private PeriodontogramRequest periodontogram;
}
