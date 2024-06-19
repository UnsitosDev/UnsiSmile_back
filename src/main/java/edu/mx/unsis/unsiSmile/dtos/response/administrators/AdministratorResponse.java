package edu.mx.unsis.unsiSmile.dtos.response.administrators;

import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorResponse {
    private String employeeNumber;
    private PersonResponse person;
    private UserResponse user;
}
