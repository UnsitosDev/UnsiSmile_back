package edu.mx.unsis.unsiSmile.authenticationProviders.dtos;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    String username;
    @NotBlank
    String password;
    @NotNull
    String role;


}
