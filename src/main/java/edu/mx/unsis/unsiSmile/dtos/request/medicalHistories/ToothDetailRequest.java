package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothDetailRequest {
    private Long idToothDetail;
    
    @NotNull(message = "Dental code cannot be null")
    private DentalCodeRequest dentalCode;
    
    @NotNull(message = "Tooth condition cannot be null")
    private ToothConditionRequest toothConditionRequest;
    
    @NotNull(message = "Tooth region cannot be null")
    private ToothRegionRequest toothRegionRequest;
    
    @NotNull(message = "Odontogram cannot be null")
    private OdontogramRequest odontogramRequest;
}
