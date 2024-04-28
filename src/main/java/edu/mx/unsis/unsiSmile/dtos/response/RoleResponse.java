package edu.mx.unsis.unsiSmile.dtos.response;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long idRole;
    private ERole role;
}
