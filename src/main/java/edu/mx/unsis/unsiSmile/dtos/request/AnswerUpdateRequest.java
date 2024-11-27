package edu.mx.unsis.unsiSmile.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerUpdateRequest extends AnswerRequest {

    @NotNull(message = "The Id Answer cannot be null.")
    private Long idAnswer;
}
