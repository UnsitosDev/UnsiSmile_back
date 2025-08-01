package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.components;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormComponentRequest {
    private Long idFormComponent;

    @NotNull(message = ResponseMessages.NOT_NULL_DESCRIPTION_FIELD)
    @NotBlank(message = ResponseMessages.NOT_BLANK_DESCRIPTION_FIELD)
    private String description;
}
