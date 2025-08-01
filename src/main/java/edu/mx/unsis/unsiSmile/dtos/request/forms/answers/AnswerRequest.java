package edu.mx.unsis.unsiSmile.dtos.request.forms.answers;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {

    @NotNull(message = "The Id Patient Clinical History cannot be null.")
    private Long idPatientMedicalRecord;

    @NotNull(message = "The Id Question cannot be null.")
    private Long idQuestion;

    private Boolean answerBoolean ;

    private BigDecimal answerNumeric;

    private String answerText;

    private LocalDate answerDate;

    private Long idCatalogOption;
}