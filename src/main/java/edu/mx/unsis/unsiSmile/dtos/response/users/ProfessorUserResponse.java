package edu.mx.unsis.unsiSmile.dtos.response.users;

import edu.mx.unsis.unsiSmile.dtos.response.students.CareerResponse;
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
public class ProfessorUserResponse extends UserBaseResponse {
    private String idProfessor;
    private CareerResponse career;
}

