package edu.mx.unsis.unsiSmile.dtos.request.periodontogram;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurfaceEvaluationRequest {
    private SurfaceType surface;
    private List<SurfaceMeasurementRequest> surfaceMeasurements;
}
