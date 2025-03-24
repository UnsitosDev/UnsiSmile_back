package edu.mx.unsis.unsiSmile.dtos.request.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest extends StudentReq{
    @NotNull(message = ResponseMessages.NOT_NULL_PERSON)
    @Valid
    private PersonRequest person;
    @NotNull(message = ResponseMessages.NOT_NULL_GROUP)
    @Valid
    private GroupRequest group;
}