package edu.mx.unsis.unsiSmile.dtos.request.students;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.UserRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest extends StudentReq{
    private UserRequest user;
    @NotNull(message = "The person field cannot be null")
    private PersonRequest person;
}