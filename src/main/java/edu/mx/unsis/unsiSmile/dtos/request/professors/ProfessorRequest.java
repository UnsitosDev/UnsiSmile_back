package edu.mx.unsis.unsiSmile.dtos.request.professors;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.people.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.CareerRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequest {
    @NotNull(message = ResponseMessages.EMPLOYEE_NUMBER_NULL)
    @Pattern(regexp = "\\d{4,6}", message = ResponseMessages.EMPLOYEE_NUMBER_INVALID)
    private String employeeNumber;

    @NotNull(message = ResponseMessages.PERSON_NULL)
    private PersonRequest person;

    @NotNull(message = ResponseMessages.CAREER_NULL)
    private CareerRequest career;
}
