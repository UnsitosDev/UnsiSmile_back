package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserBaseResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseUserResponseBuilder{

    public UserBaseResponse build(UserModel userModel) {
        return UserBaseResponse.builder()
                .username(userModel.getUsername())
                .role(userModel.getRole().getRole().toString())
                .build();
    }
}