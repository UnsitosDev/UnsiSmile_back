package edu.mx.unsis.unsiSmile.dtos.request.addresses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateRequest {
    @NotBlank(message = "State ID cannot be blank")
    @Size(min = 2, max = 2, message = "State ID must be 2 characters long")
    private String idState;

    @NotBlank(message = "State name cannot be blank")
    @Size(max = 50, message = "State name must be at most 50 characters long")
    private String name;
}