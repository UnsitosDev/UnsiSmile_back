package edu.mx.unsis.unsiSmile.dtos.request.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = ResponseMessages.NOT_NULL_ENROLLMENT)
    @Size(min = 10, max = 10, message = ResponseMessages.ENROLLMENT_INVALID)
    private String idStudent;
}