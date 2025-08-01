package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserBaseResponse;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import org.springframework.stereotype.Component;

@Component
public interface UserBaseMapper {
    UserBaseResponse toDto(UserModel userModel);
    UserBaseResponse toDto(AdministratorModel admin);
    UserBaseResponse toDto(ProfessorModel professor);
    UserBaseResponse toDto(StudentModel student);
    UserBaseResponse toDto(MedicalRecordDigitizerModel digitizer);
}
