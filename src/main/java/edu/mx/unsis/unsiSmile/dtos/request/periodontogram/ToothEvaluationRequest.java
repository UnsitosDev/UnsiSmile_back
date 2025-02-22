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
public class ToothEvaluationRequest {
    private String idTooth;
    private Integer mobility;
    private List<SurfaceEvaluationRequest> surfaceEvaluations;
}
