package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaceRequest {
    private Long idFace;
    private List<ConditionRequest> conditions;
}
