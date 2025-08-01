package edu.mx.unsis.unsiSmile.dtos.request.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentScopeRequest {

    private Long idScopeTreatment;

    @NotNull(message = ResponseMessages.NOT_NULL_TREATMENT_SCOPE)
    @NotBlank(message = ResponseMessages.NOT_BLANK_TREATMENT_SCOPE)
    private String name;
}