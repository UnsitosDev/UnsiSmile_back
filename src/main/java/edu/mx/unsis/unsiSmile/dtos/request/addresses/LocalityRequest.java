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
public class LocalityRequest {
    @NotBlank(message = "Locality ID cannot be blank")
    @Size(min = 5, max = 5, message = "Locality ID must be 5 characters long")
    private String idLocality;

    @NotBlank(message = "Locality name cannot be blank")
    @Size(max = 50, message = "Locality name must be at most 50 characters long")
    private String name;

    @Size(min = 5, max = 5, message = "Postal code must be 5 characters long")
    private String postalCode;

    @NotNull(message = "Municipality cannot be null")
    private MunicipalityRequest municipality;
}