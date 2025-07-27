package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.dtos.response.users.StudentUserResponse;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StudentResponseBuilder {

    public StudentUserResponse build(StudentModel student) {
        return StudentUserResponse.builder()
                .username(student.getUser().getUsername())
                .email(student.getPerson().getEmail())
                .role(student.getUser().getRole().getRole().toString())
                .fullName(student.getPerson().getFullName()) // adjust as needed
                .enrollment(student.getEnrollment())
                .build();
    }
}
