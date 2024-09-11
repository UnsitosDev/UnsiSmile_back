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
public class QuestionRequest {

    @NotNull(message = "The Question Description cannot be null.")
    private String questionText;

    @NotNull(message = "The Id Form Section cannot be null.")
    private Long idFormSectionModel;

    @NotNull(message = "The Id Answer cannot be null.")
    private Long idAnswerTypeModel;

    private Long idCatalogModel;
}
