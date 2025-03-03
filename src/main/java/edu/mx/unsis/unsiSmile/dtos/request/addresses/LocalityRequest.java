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
public class LocalityRequest {
    private Long idLocality;

    @NotBlank(message = ResponseMessages.LOCALITY_NAME_BLANK)
    @NotNull(message = ResponseMessages.LOCALITY_NAME_NULL)
    @Size(max = 50, message = ResponseMessages.LOCALITY_NAME_SIZE)
    private String name;

    @NotBlank(message = ResponseMessages.POSTAL_CODE_BLANK)
    @NotNull(message = ResponseMessages.POSTAL_CODE_NULL)
    @Size(min = 5, max = 5, message = ResponseMessages.POSTAL_CODE_SIZE)
    private String postalCode;

    @NotNull(message = ResponseMessages.MUNICIPALITY_NULL)
    @Valid
    private MunicipalityRequest municipality;
}