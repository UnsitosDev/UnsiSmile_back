package edu.mx.unsis.unsiSmile.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRequest {

    @NotBlank(message = "CURP cannot be blank")
    @Size(min = 18, max = 18, message = "CURP must be 18 characters long")
    private String curp;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name must be at most 50 characters long")
    private String firstName;

    @Size(max = 50, message = "Second name must be at most 50 characters long")
    private String secondName;

    @NotBlank(message = "First last name cannot be blank")
    @Size(max = 50, message = "First last name must be at most 50 characters long")
    private String firstLastName;

    @Size(max = 50, message = "Second last name must be at most 50 characters long")
    private String secondLastName;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long")
    private String phone;

    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 200, message = "Email must be at most 200 characters long")
    private String email;

    @NotNull(message = "Gender cannot be null")
    private GenderRequest gender;
}