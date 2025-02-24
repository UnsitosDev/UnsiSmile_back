package edu.mx.unsis.unsiSmile.dtos.response.periodontograms;

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
public class SurfaceEvaluationResponse {
    private Long id;
    private String surface;
    private List<SurfaceMeasurementResponse> surfaceMeasurements;
}
