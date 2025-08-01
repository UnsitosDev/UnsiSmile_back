package edu.mx.unsis.unsiSmile.dtos.request.digitizers;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDigitizerRequest {

    private Long idMedicalRecordDigitizer;

    @NotBlank(message = ResponseMessages.NOT_BLANK_USERNAME)
    private String username;
}