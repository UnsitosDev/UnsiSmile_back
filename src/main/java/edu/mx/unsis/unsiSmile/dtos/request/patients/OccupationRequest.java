package edu.mx.unsis.unsiSmile.dtos.request.patients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OccupationRequest {
    @NotBlank(message = "Occupation ID cannot be blank")
    private Long idOccupation;

    @NotBlank(message = "Occupation description cannot be blank")
    @Size(max = 100, message = "Occupation description must be at most 100 characters long")
    private String occupation;
}