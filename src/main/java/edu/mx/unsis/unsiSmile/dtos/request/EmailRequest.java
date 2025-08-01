package edu.mx.unsis.unsiSmile.dtos.request;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @NotEmpty(message = ResponseMessages.EMAIL_RECIPIENTS_CANNOT_BE_NULL)
    private List<@Email(message = ResponseMessages.INVALID_EMAIL_ADDRESS) String> to;

    private List<@Email(message = ResponseMessages.INVALID_EMAIL_ADDRESS) String> cc;

    @NotBlank(message = ResponseMessages.EMAIL_SUBJECT_CANNOT_BE_NULL)
    private String subject;

    private String textBody;

    private String htmlBody;
}