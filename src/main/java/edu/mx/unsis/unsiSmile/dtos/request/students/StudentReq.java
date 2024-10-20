package edu.mx.unsis.unsiSmile.dtos.request.students;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentReq {
    @NotBlank(message = "The enrollment field cannot be blank")
    @NotNull(message = "The enrollment field cannot be null")
    private String enrollment;
}
