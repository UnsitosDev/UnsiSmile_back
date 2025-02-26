package edu.mx.unsis.unsiSmile.dtos.request.professors;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorGroupRequest {
    Long idProfessorGroup;

    @NotNull(message = "The idProfessor field cannot be null")
    String employeNumber;

    @NotNull(message = "The idGroup field cannot be null")
    Long idGroup;
}
