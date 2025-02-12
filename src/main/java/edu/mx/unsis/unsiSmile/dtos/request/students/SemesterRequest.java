package edu.mx.unsis.unsiSmile.dtos.request.students;

import java.time.LocalDate;

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
    @NotNull(message = "The field startDate can't be null")
    private LocalDate starDate;

    @NotNull(message = "The field endDate can't be null")
    private LocalDate endDate;

    @NotNull(message = "The field Cycle can't be null")
    private CycleRequest cycle;
}
