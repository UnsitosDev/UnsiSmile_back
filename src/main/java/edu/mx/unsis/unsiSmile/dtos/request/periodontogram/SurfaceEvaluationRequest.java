package edu.mx.unsis.unsiSmile.dtos.request.periodontogram;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurfaceEvaluationRequest {

    private SurfaceType surface;
    private List<SurfaceMeasurementRequest> surfaceMeasurements;
}