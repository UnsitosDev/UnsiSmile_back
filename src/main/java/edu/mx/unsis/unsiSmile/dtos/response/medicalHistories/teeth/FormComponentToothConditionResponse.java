package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothConditionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormComponentToothConditionResponse {
    private Long id;

    private FormComponentResponse formComponent;

    private ToothConditionResponse toothCondition;
}