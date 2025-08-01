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
public class EthnicGroupRequest {
    private Long idEthnicGroup;

    @NotBlank(message = ResponseMessages.NOT_BLANK_ETHNIC_GROUP_DESCRIPTION)
    @Size(max = 100, message = ResponseMessages.MAX_SIZE_ETHNIC_GROUP_DESCRIPTION)
    private String ethnicGroup;
}