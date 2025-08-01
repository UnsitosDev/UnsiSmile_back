package edu.mx.unsis.unsiSmile.dtos.request.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentReq {

    @NotBlank(message = ResponseMessages.NOT_NULL_ENROLLMENT)
    @NotNull(message = ResponseMessages.NOT_NULL_ENROLLMENT)
    @Pattern(regexp = "\\d{10}", message = ResponseMessages.ENROLLMENT_INVALID)
    private String enrollment;
}