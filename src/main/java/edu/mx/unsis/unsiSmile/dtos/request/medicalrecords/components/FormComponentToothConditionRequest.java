package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.components;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormComponentToothConditionRequest {

    private Long id;

    @NotNull(message = ResponseMessages.NOT_NULL_ID_FORM_COMPONENT)
    @Positive(message = ResponseMessages.POSITIVE_ID_FORM_COMPONENT)
    private Long idFormComponent;

    @NotNull(message = ResponseMessages.NOT_NULL_ID_TOOTH_CONDITION)
    @Positive(message = ResponseMessages.POSITIVE_ID_TOOTH_CONDITION)
    private Long idToothCondition;
}