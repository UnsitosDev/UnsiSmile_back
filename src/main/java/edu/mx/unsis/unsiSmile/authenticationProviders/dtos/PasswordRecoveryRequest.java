package edu.mx.unsis.unsiSmile.authenticationProviders.dtos;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryRequest {
    @Email(message = ResponseMessages.INVALID_EMAIL_ADDRESS)
    @NotBlank(message = ResponseMessages.NOT_BLANK_EMAIL)
    private String email;
}