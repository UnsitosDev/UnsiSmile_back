package edu.mx.unsis.unsiSmile.dtos.request.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
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
    @NotNull(message = ResponseMessages.NOT_NULL_PERSON)
    private PersonRequest person;
    @NotNull(message = ResponseMessages.NOT_NULL_GROUP)
    private GroupRequest group;
}