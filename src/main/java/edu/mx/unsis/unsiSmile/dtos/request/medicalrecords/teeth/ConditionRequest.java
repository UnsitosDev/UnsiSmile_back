package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionRequest {
    @NotNull(message = "The condition ID cannot be null.")
    private Long idCondition;

}
