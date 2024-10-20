package edu.mx.unsis.unsiSmile.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerResponse {

    private Long idAnswer;

    private Boolean answerBoolean;

    private BigDecimal answerNumeric;

    private String answerText;

    private LocalDate answerDate;

    private CatalogOptionResponse answerCatalogOption;

    private List<FileResponse> files;
}
