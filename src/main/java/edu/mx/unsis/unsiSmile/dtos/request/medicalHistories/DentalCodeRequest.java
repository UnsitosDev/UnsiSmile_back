package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;
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
public class DentalCodeRequest {
    private Long idDentalCode;
    
    @NotBlank(message = "Code cannot be blank")
    @Size(min = 2, max = 3, message = "Code must be 2 characters long")
    private String code;
    
    @NotNull(message = "the field adult cannot be null")
    private Boolean adult;
}
