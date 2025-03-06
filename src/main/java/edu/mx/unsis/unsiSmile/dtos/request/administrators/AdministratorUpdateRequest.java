package edu.mx.unsis.unsiSmile.dtos.request.administrators;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministratorUpdateRequest {
    private String employeeNumber;
    private PersonRequest person;
}
