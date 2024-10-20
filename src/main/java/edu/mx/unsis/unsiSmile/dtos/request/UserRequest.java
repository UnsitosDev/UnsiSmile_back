package edu.mx.unsis.unsiSmile.dtos.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private UUID idUser;
    //@NotBlank(message = "The Username field cannot be null")
    //@NotBlank(message = "The Username field cannot be blank")
    private String username;

    //@NotBlank(message = "The Password field cannot be null")
    //@NotBlank(message = "The Password field cannot be blank")
    private String password;

    @NotBlank(message = "The Role field cannot be null")
    private RoleRequest role;
}
