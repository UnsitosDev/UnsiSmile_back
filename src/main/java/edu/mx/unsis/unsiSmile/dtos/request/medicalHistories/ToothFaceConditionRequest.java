package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothFaceConditionRequest {
    private Long idToothFaceCondition;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 50, message = "Description must be less than or equal to 50 characters")
    private String description;
}
