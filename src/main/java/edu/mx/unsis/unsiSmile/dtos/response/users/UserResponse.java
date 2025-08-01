package edu.mx.unsis.unsiSmile.dtos.response.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private RoleResponse role;
    private Boolean status;
    private String profilePictureId;
}