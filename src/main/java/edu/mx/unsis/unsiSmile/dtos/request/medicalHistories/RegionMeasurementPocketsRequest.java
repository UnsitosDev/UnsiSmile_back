package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionMeasurementPocketsRequest {
    private Long idRegionsMeasurementPockets;
    
    @NotBlank(message = "Region cannot be blank")
    private String region;
}
