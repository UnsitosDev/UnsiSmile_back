package edu.mx.unsis.unsiSmile.dtos.request.forms.answers;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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