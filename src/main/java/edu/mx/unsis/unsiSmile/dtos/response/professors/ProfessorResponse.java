package edu.mx.unsis.unsiSmile.dtos.response.professors;

import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.CareerResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorResponse {
    private Long idProfessorClinicalArea;
    private String employeeNumber;
    private PersonResponse person;
    private UserResponse user;
    private CareerResponse career;
    private String professorStatus;
}