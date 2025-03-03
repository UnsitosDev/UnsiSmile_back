package edu.mx.unsis.unsiSmile.dtos.request.addresses;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.Valid;
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
public class MunicipalityRequest {
    @NotBlank(message = ResponseMessages.MUNICIPALITY_ID_BLANK)
    @Size(min = 4, max = 4, message = ResponseMessages.MUNICIPALITY_ID_SIZE)
    private String idMunicipality;

    @NotBlank(message = ResponseMessages.MUNICIPALITY_NAME_BLANK)
    @Size(max = 50, message = ResponseMessages.MUNICIPALITY_NAME_SIZE)
    private String name;

    @NotNull(message = ResponseMessages.STATE_NULL)
    @Valid
    private StateRequest state;
}