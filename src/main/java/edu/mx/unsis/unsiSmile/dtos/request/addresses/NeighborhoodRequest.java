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
public class NeighborhoodRequest {
    
    private Long idNeighborhood;

    @NotBlank(message = ResponseMessages.NEIGHBORHOOD_NAME_BLANK)
    @NotNull(message = ResponseMessages.NEIGHBORHOOD_NAME_NULL)
    @Size(max = 50, message = ResponseMessages.NEIGHBORHOOD_NAME_SIZE)
    private String name;

    @NotNull(message = ResponseMessages.LOCALITY_NULL)
    @Valid
    private LocalityRequest locality;
}