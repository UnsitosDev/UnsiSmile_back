package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramRequest {
    @NotNull(message = "The request can't be null")
    private List<ToothRequest> tooths;
    @NotNull(message = "The patient id can't be null")
    private Long idPatient;
    @NotNull(message = "The odontogram type cannot be null")
    private TypeOdontogram typeOdontogram;
}
