package edu.mx.unsis.unsiSmile.dtos.request.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareerRequest {

    @NotNull(message = ResponseMessages.NOT_NULL_ID_CAREER)
    @NotBlank(message = ResponseMessages.NOT_BLANK_ID_CAREER)
    private String idCareer;

    @NotNull(message = ResponseMessages.NOT_NULL_CAREER)
    @NotBlank(message = ResponseMessages.NOT_BLANK_CAREER)
    private String career;
}