package edu.mx.unsis.unsiSmile.dtos.request.students;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterRequest {
    private Long idSemester;

    @NotNull(message = "The field Group can't be null")
    private GroupRequest group;

    @NotNull(message = "The field Cycle can't be null")
    private CycleRequest cycle;
}
