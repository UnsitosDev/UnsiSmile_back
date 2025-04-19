package edu.mx.unsis.unsiSmile.dtos.response.professors;

import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.CareerResponse;
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
