package edu.mx.unsis.unsiSmile.dtos.response.students;


import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String enrollment;
    private UserResponse user;
    private PersonResponse person;
    private GroupResponse group;
    private String studentStatus;
}