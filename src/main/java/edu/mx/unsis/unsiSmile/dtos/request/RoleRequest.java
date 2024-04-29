package edu.mx.unsis.unsiSmile.dtos.request;


import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private Long idRole;
    @NotBlank(message = "The Role field cannot be null")
    @NotBlank(message = "The Role field cannot be blank")
    private ERole role;
}

