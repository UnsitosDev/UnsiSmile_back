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
public class StreetRequest {
    private Long idStreet;

    @NotBlank(message = "Street name cannot be blank")
    @Size(max = 50, message = "Street name must be at most 50 characters long")
    private String name;

    @NotNull(message = "Neighborhood cannot be null")
    private NeighborhoodRequest neighborhood;
}