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
public class StreetRequest {

    private Long idStreet;

    @NotBlank(message = ResponseMessages.STREET_NAME_BLANK)
    @NotNull(message = ResponseMessages.STREET_NAME_NULL)
    @Size(max = 50, message = ResponseMessages.STREET_NAME_SIZE)
    private String name;

    @NotNull(message = ResponseMessages.NEIGHBORHOOD_NULL)
    @Valid
    private NeighborhoodRequest neighborhood;
}