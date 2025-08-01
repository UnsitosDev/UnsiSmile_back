package edu.mx.unsis.unsiSmile.dtos.request.forms.questions;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionRequest {

    @NotNull(message = "The Question Description cannot be null.")
    private String questionText;

    private String placeholder;

    @NotNull(message = "The attribute Required cannot be null.")
    private Boolean isRequired;

    private Long order;

    @NotNull(message = "The Id Form Section cannot be null.")
    private String idFormSection;

    @NotNull(message = "The Id Answer Type cannot be null.")
    private Long idAnswerType;

    private Long idCatalog;
}
