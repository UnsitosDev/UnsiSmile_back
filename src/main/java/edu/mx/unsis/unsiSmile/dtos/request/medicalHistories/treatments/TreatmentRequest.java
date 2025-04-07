package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentRequest {

    private Long idTreatment;

    @NotBlank(message = ResponseMessages.NOT_BLANK_TREATMENT_NAME)
    @Size(max = 100, message = ResponseMessages.MAX_LENGTH_TREATMENT_NAME)
    private String name;

    @NotNull(message = ResponseMessages.NOT_NULL_SCOPE_TYPE_ID)
    @Positive(message = ResponseMessages.POSITIVE_SCOPE_TYPE_ID)
    private Long scopeTypeId;

    @Digits(integer = 8, fraction = 2, message = ResponseMessages.DIGITS_TREATMENT_COST)
    private BigDecimal cost;

    @NotNull(message = ResponseMessages.NOT_NULL_CATALOG_ID)
    @Positive(message = ResponseMessages.POSITIVE_CATALOG_ID)
    private Long clinicalHistoryCatalogId;
}