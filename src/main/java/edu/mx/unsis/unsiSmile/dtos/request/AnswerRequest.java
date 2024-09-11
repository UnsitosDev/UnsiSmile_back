package edu.mx.unsis.unsiSmile.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerRequest {

    @NotNull(message = "The Id Patient Clinical History cannot be null.")
    private Long idPatientClinicalHistoryModel;

    @NotNull(message = "The Id Question cannot be null.")
    private Long idQuestionModel;

    private Boolean answerBoolean;

    private BigDecimal answerNumeric;

    private String answerText;

    private LocalDateTime answerDate;

    private Long idCatalogOptionModel;

    private String idFileModel;
}
