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
public class MunicipalityRequest {
    @NotBlank(message = "Municipality ID cannot be blank")
    @Size(min = 4, max = 4, message = "Municipality ID must be 4 characters long")
    private String idMunicipality;

    @NotBlank(message = "Municipality name cannot be blank")
    @Size(max = 50, message = "Municipality name must be at most 50 characters long")
    private String name;

    @NotNull(message = "State cannot be null")
    private StateRequest state;
}