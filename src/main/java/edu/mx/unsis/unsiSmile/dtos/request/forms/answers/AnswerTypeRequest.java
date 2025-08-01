package edu.mx.unsis.unsiSmile.dtos.request.forms.answers;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerTypeRequest {

    @NotNull(message = "The Answer Type cannot be null")
    private String description;
}
