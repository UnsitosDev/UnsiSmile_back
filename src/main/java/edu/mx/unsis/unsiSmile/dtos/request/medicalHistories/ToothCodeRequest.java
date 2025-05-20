package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ToothCodeRequest {

    @NotNull(message = ResponseMessages.NOT_NULL_TREATMENT_ID)
    @Positive(message = ResponseMessages.POSITIVE_TREATMENT_ID)
    private Long idTreatment;

    @NotEmpty(message = ResponseMessages.NOT_EMPTY_TEETH_LIST)
    @Size(max = 6, message = ResponseMessages.TEETH_LIST_SIZE)
    @Valid
    private List<ToothReq> teeth;

    @Data
    @Builder
    public static class ToothReq {

        @NotNull(message = ResponseMessages.NOT_NULL_TOOTH_CODE)
        private EnumToothId idTooth;

        @NotNull(message = ResponseMessages.NOT_NULL_CODE_VALUE)
        @Min(value = 0, message = ResponseMessages.MIN_CODE_VALUE)
        @Max(value = 4, message = ResponseMessages.MAX_CODE_VALUE)
        private Integer code;
    }
}