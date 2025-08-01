package edu.mx.unsis.unsiSmile.dtos.response.people;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonResponse {
    private String curp;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private GenderResponse gender;

    public String getFullName() {
        return firstName + " " +
                ((secondName != null && !secondName.trim().isEmpty()) ? secondName + " " : "") +
                firstLastName + " " +
                secondLastName;
    }
}