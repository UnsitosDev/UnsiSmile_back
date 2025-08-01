package edu.mx.unsis.unsiSmile.dtos.request.patients.demographics;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
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

    @NotBlank(message = ResponseMessages.NOT_BLANK_MARITAL_STATUS_DESCRIPTION)
    @Size(max = 100, message = ResponseMessages.MAX_SIZE_MARITAL_STATUS_DESCRIPTION)
    private String maritalStatus;
}