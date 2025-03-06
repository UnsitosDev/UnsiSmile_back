package edu.mx.unsis.unsiSmile.dtos.request.patients;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReligionRequest {

    private Long idReligion;

    @NotBlank(message = ResponseMessages.NOT_BLANK_RELIGION_DESCRIPTION)
    @Size(max = 100, message = ResponseMessages.MAX_SIZE_RELIGION_DESCRIPTION)
    private String religion;
}