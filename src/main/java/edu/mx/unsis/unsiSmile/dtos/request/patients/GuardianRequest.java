package edu.mx.unsis.unsiSmile.dtos.request.patients;

import edu.mx.unsis.unsiSmile.dtos.request.CatalogOptionRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuardianRequest {
    private Long idGuardian;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name must be at most 50 characters long")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name must be at most 50 characters long")
    private String lastName;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(max = 20, message = "Phone number must be at most 20 characters long")
    private String phone;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 50, message = "Email must be at most 50 characters long")
    private String email;

    @NotNull(message = "Parental status is required")
    private CatalogOptionRequest parentalStatus;

    @Size(max = 100, message = "Doctor name must be at most 100 characters long")
    private String doctorName; 
}