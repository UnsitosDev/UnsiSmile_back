package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScopeTypeRequest {
    private Long idScopeType;

    @NotNull(message = ResponseMessages.NOT_NULL_SCOPE_TYPE)
    @NotBlank(message = ResponseMessages.NOT_BLANK_SCOPE_TYPE)
    private String name;
}