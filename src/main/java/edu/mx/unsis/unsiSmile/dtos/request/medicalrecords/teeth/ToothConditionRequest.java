package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth;

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
public class ToothConditionRequest {
    private Long idToothCondition;
    
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 50, message = "Description must be less than or equal to 50 characters")
    private String description;
}