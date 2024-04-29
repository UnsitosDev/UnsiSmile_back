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
public class NationalityRequest {
    private Long idNationality;

    @NotBlank(message = "Nationality description cannot be blank")
    @Size(max = 100, message = "Nationality description must be at most 100 characters long")
    private String nationality;
}