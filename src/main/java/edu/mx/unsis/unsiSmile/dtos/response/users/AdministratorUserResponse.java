package edu.mx.unsis.unsiSmile.dtos.response.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AdministratorUserResponse extends UserBaseResponse {
    private String employeeNumber;
}