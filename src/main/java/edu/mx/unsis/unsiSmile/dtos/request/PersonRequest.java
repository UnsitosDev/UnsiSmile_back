package edu.mx.unsis.unsiSmile.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRequest {

    @NotBlank(message = ResponseMessages.NOT_BLANK_CURP)
    @Size(min = 18, max = 18, message = ResponseMessages.SIZE_CURP)
    private String curp;

    @NotBlank(message = ResponseMessages.NOT_BLANK_FIRST_NAME)
    @Size(max = 50, message = ResponseMessages.SIZE_FIRST_NAME)
    private String firstName;

    @Size(max = 50, message = ResponseMessages.SIZE_SECOND_NAME)
    private String secondName;

    @NotBlank(message = ResponseMessages.NOT_BLANK_FIRST_LAST_NAME)
    @Size(max = 50, message = ResponseMessages.SIZE_FIRST_LAST_NAME)
    private String firstLastName;

    @Size(max = 50, message = ResponseMessages.SIZE_SECOND_LAST_NAME)
    private String secondLastName;

    @Pattern(regexp = "^\\d{10}$", message = ResponseMessages.PATTERN_PHONE)
    private String phone;

    @NotNull(message = ResponseMessages.NOT_NULL_BIRTH_DATE)
    private LocalDate birthDate;

    @NotBlank(message = ResponseMessages.NOT_BLANK_EMAIL)
    @Email(message = ResponseMessages.VALID_EMAIL)
    @Size(max = 200, message = ResponseMessages.SIZE_EMAIL)
    private String email;

    @NotNull(message = ResponseMessages.NOT_NULL_GENDER)
    private GenderRequest gender;
}