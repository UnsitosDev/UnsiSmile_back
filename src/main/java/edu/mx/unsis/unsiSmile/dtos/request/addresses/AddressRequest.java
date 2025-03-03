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
public class AddressRequest {
    private Long idAddress;

    @NotBlank(message = ResponseMessages.STREET_NUMBER_BLANK)
    @Size(max = 2, message = ResponseMessages.STREET_NUMBER_SIZE)
    private String streetNumber;

    @Size(max = 2, message = ResponseMessages.INTERIOR_NUMBER_SIZE)
    private String interiorNumber;

    @NotNull(message = ResponseMessages.HOUSING_NULL)
    @Valid
    private HousingRequest housing;

    @NotNull(message = ResponseMessages.STREET_NULL)
    @Valid
    private StreetRequest street;
}