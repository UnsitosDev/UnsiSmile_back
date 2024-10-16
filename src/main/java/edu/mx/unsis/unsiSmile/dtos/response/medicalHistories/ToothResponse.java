package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothResponse {
    private String idTooth;
    private List<FaceResponse> faces;
    private boolean isAdult;
    private List<ConditionResponse> conditions;
}
