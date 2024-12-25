package edu.mx.unsis.unsiSmile.dtos.request.students;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentPatientRequest {

    @NotNull(message = "Patient cannot be null")
    private UUID patientId;

    @NotNull(message = "Student cannot be null")
    private String studentEnrollment;
}
