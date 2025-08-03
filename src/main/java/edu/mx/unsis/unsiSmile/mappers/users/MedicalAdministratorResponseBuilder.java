package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.dtos.response.users.AdministratorUserResponse;
import edu.mx.unsis.unsiSmile.model.administrators.MedicalAdministratorModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MedicalAdministratorResponseBuilder {

    public AdministratorUserResponse build(MedicalAdministratorModel admin) {
       
        return AdministratorUserResponse.builder()
                .username(admin.getUser().getUsername())
                .role(admin.getUser().getRole().getRole().toString())
                .employeeNumber(admin.getEmployeeNumber())
                .email(admin.getPerson().getEmail())
                .fullName(admin.getPerson().getFullName())
                .build();
    }
}