package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserBaseResponse;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.MedicalRecordDigitizerModel;
import org.springframework.stereotype.Component;

@Component
public class UserBaseMapperImpl implements UserBaseMapper {
    @Override
    public UserBaseResponse toDto(UserModel userModel) {
        return UserBaseResponse.builder()
                .username(userModel.getUsername())
                .role(userModel.getRole().getRole().toString())
                .build();
    }

    @Override
    public UserBaseResponse toDto(AdministratorModel admin) {
        return UserBaseResponse.builder()
                .username(admin.getUser().getUsername())
                .role(admin.getUser().getRole().getRole().toString())
                .build();
    }

    @Override
    public UserBaseResponse toDto(ProfessorModel professor) {
        return UserBaseResponse.builder()
                .username(professor.getUser().getUsername())
                .role(professor.getUser().getRole().getRole().toString())
                .build();
    }

    @Override
    public UserBaseResponse toDto(StudentModel student) {
        return UserBaseResponse.builder()
                .username(student.getUser().getUsername())
                .role(student.getUser().getRole().getRole().toString())
                .build();
    }

    @Override
    public UserBaseResponse toDto(MedicalRecordDigitizerModel digitizer) {
        return UserBaseResponse.builder()
                .username(digitizer.getUser().getUsername())
                .role(digitizer.getUser().getRole().getRole().toString())
                .build();
    }
}
