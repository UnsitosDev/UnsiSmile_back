package edu.mx.unsis.unsiSmile.dtos.request.students;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGroupRequest {

    @NotNull(message = "The field enrollment cannot be null.")
    private String enrollment;

    @NotNull(message = "The field groupId cannot be null.")
    private Long groupId;
}