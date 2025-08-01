package edu.mx.unsis.unsiSmile.dtos.request.addresses;

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
public class StateRequest {

    @NotBlank(message = ResponseMessages.STATE_ID_BLANK)
    @Size(min = 2, max = 2, message = ResponseMessages.STATE_ID_SIZE)
    private String idState;

    @NotBlank(message = ResponseMessages.STATE_NAME_BLANK)
    @Size(max = 50, message = ResponseMessages.STATE_NAME_SIZE)
    private String name;
}