package edu.mx.unsis.unsiSmile.dtos.request.people;

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
public class GenderRequest {

    @NotNull(message = ResponseMessages.NOT_NULL_ID_GENDER_FIELD)
    private Long idGender;

    @NotNull(message = ResponseMessages.NOT_NULL_GENDER_FIELD)
    @NotBlank(message = ResponseMessages.NOT_BLANK_GENDER_FIELD)
    private String gender;
}