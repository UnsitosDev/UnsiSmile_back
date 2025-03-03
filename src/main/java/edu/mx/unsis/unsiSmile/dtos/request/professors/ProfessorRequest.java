package edu.mx.unsis.unsiSmile.dtos.request.professors;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.CareerRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequest {
    @NotNull(message = "The employeeNumber field cannot be null")
    private String employeeNumber;

    @NotNull(message = "The person field cannot be null")
    private PersonRequest person;

    @NotNull(message = "The career field cannot be null")
    private CareerRequest career;
}
