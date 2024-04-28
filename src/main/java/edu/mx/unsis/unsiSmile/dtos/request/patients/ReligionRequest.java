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
public class ReligionRequest {
    @NotBlank(message = "Religion ID cannot be blank")
    private Long idReligion;

    @NotBlank(message = "Religion description cannot be blank")
    @Size(max = 100, message = "Religion description must be at most 100 characters long")
    private String religion;
}