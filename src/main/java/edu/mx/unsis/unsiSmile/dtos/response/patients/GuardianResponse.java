package edu.mx.unsis.unsiSmile.dtos.response.patients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuardianResponse {
    private Long idGuardian;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String parentalStatus;  
    private String doctorName; 
}