package edu.mx.unsis.unsiSmile.dtos.response.periodontograms;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothEvaluationResponse {
    private Long id;
    private String idTooth;
    private Integer mobility;
    private List<SurfaceEvaluationResponse> surfaceEvaluations;
}