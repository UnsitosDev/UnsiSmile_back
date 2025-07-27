package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.dtos.response.users.AdministratorUserResponse;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdministratorResponseBuilder {

    public AdministratorUserResponse build(AdministratorModel admin) {
       
        return AdministratorUserResponse.builder()
                .username(admin.getUser().getUsername())
                .role(admin.getUser().getRole().getRole().toString())
                .employeeNumber(admin.getEmployeeNumber())
                .email(admin.getPerson().getEmail())
                .fullName(admin.getPerson().getFullName())
                .build();
    }
}