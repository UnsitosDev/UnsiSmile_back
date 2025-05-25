package edu.mx.unsis.unsiSmile.authenticationProviders.dtos;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.Email;
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
public class OtpValidationRequest {

    @Email(message = ResponseMessages.INVALID_EMAIL_ADDRESS)
    @NotBlank(message = ResponseMessages.NOT_BLANK_EMAIL)
    private String email;

    @NotBlank(message = ResponseMessages.NOT_BLANK_OTP)
    @Size(min = 6, max = 6, message = ResponseMessages.INVALID_OTP_SIZE)
    private String otp;
}
