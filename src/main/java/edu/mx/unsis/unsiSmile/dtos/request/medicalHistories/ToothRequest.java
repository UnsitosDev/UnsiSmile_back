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
public class ToothRequest {
    private Long idTooth;
    private Boolean status;
    private List<ConditionRequest> conditions;
    private List<FaceRequest> faces;

}
