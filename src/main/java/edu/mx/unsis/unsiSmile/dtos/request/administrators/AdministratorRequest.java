package edu.mx.unsis.unsiSmile.dtos.request.administrators;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.UserRequest;
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
public class AdministratorRequest {
    @NotBlank(message = "The employee number field cannot be blank")
    @NotNull(message = "The employee number field cannot be null")
    private String employeeNumber;

    @NotNull(message = "The person field cannot be null")
    private PersonRequest person;

    private UserRequest user;
}
