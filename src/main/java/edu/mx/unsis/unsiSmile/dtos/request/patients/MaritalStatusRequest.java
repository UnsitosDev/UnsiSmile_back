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
public class MaritalStatusRequest {
    private Long idMaritalStatus;

    @NotBlank(message = "Marital Status description cannot be blank")
    @Size(max = 100, message = "Marital Status description must be at most 100 characters long")
    private String maritalStatus;
}