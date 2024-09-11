package edu.mx.unsis.unsiSmile.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationRequest {

    @NotNull(message = "The Validation Value cannot be null.")
    private String validationValue;

    @NotNull(message = "The Validation Message cannot be null.")
    private String validationMessage;

    @NotNull(message = "The Id Validation Type cannot be null.")
    private Long idValidationType;
}
