package edu.mx.unsis.unsiSmile.dtos.request.periodontogram;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurfaceMeasurementRequest {

    private ToothPosition toothPosition;
    private Double pocketDepth;
    private Double lesionLevel;
    private Boolean plaque;
    private Boolean bleeding;
    private Boolean calculus;
}