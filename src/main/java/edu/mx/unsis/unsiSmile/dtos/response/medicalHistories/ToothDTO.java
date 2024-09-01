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
public class ToothDTO {
    private String idTooth;
    private List<FaceDTO> faces;
    private boolean status;
    private List<ConditionResponse> conditions;
}
