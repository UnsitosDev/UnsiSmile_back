package edu.mx.unsis.unsiSmile.dtos.request.forms.answers;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerUpdateRequest extends AnswerRequest {

    @NotNull(message = "The Id Answer cannot be null.")
    private Long idAnswer;
}
