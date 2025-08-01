package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.dtos.response.users.ProfessorUserResponse;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProfessorResponseBuilder{
    
    public ProfessorUserResponse build(ProfessorModel userModel){
        return ProfessorUserResponse.builder()
                .username(userModel.getUser().getUsername())
                .email(userModel.getPerson().getEmail())
                .fullName(userModel.getPerson().getFullName())
                .role(userModel.getUser().getRole().getRole().toString())
                .build();
    }
}