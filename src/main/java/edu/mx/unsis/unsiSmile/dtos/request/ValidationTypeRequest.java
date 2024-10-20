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
public class ValidationTypeRequest {

    @NotNull(message = "The Validation Code cannot be null.")
    private String validationCode;
}
