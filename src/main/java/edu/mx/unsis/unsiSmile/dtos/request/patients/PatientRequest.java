package edu.mx.unsis.unsiSmile.dtos.request.patients;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentReq;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequest {
    private Long idPatient;

    @NotNull(message = "Admission date cannot be null")
    private LocalDate admissionDate;

    @NotNull(message = "isMinor cannot be null")
    private Boolean isMinor;

    @NotNull(message = "hasDisability cannot be null")
    private Boolean hasDisability;

    @NotNull(message = "nationalityId cannot be null")
    private Long nationalityId;

    @NotNull(message = "Person cannot be null")
    private PersonRequest person;

    @NotNull(message = "Address cannot be null")
    private AddressRequest address;

    @NotNull(message = "maritalStatusId cannot be null")
    private Long maritalStatusId;

    @NotNull(message = "occupationId cannot be null")
    private Long occupationId;

    @NotNull(message = "ethnicGroupId cannot be null")
    private Long ethnicGroupId;

    @NotNull(message = "religionId cannot be null")
    private Long religionId;

    private GuardianRequest guardian;

    private StudentReq student;
}
