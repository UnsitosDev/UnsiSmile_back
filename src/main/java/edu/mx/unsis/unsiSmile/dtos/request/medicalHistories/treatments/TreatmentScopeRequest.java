package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentScopeRequest {

    private Long idScope;

    @NotNull(message = ResponseMessages.NOT_NULL_SCOPE_TYPE_ID)
    @Positive(message = ResponseMessages.POSITIVE_SCOPE_TYPE_ID)
    private Long scopeTypeId;

    @NotBlank(message = ResponseMessages.NOT_BLANK_SCOPE_NAME)
    @NotNull(message = ResponseMessages.NOT_NULL_SCOPE_NAME)
    private String scopeName;
}