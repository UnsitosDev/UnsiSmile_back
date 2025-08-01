package edu.mx.unsis.unsiSmile.dtos.request.periodontogram;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothEvaluationRequest {

    private String idTooth;
    private Integer mobility;
    private List<SurfaceEvaluationRequest> surfaceEvaluations;
}