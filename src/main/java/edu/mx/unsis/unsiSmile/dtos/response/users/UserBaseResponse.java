package edu.mx.unsis.unsiSmile.dtos.response.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserBaseResponse {
    private String username;
    private String role;
    private String email;
    private String fullName;
}
