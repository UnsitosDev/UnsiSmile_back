package edu.mx.unsis.unsiSmile.dtos.response.periodontograms;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurfaceMeasurementResponse {
    private Long id;
    private String toothPosition;
    private Double pocketDepth;
    private Double lesionLevel;
    private Boolean plaque;
    private Boolean bleeding;
    private Boolean calculus;
}