package edu.mx.unsis.unsiSmile.dtos.response.periodontograms;

import lombok.*;

import java.util.List;

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