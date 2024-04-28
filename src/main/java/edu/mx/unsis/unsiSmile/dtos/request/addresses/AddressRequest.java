package edu.mx.unsis.unsiSmile.dtos.request.addresses;

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

    @NotBlank(message = "Street number cannot be blank")
    @Size(max = 2, message = "Street number must be at most 2 characters long")
    private String streetNumber;

    @Size(max = 2, message = "Interior number must be at most 2 characters long")
    private String interiorNumber;

    @NotNull(message = "Housing cannot be null")
    private HousingRequest housing;

    @NotNull(message = "Street cannot be null")
    private StreetRequest street;
}