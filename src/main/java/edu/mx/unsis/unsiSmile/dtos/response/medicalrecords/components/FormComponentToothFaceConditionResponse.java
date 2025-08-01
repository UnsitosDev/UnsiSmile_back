package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.components;

import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothFaceConditionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormComponentToothFaceConditionResponse {
    private Long id;

    private FormComponentResponse formComponent;

    private ToothFaceConditionResponse toothFaceCondition;
}