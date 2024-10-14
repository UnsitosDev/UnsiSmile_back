package edu.mx.unsis.unsiSmile.mappers.students;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRes {
    private String enrollment;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
}
