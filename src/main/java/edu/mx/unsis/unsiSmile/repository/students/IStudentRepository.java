package edu.mx.unsis.unsiSmile.repository.students;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;

@Repository
public interface IStudentRepository extends JpaRepository<StudentModel, String> {

        Optional<StudentModel> findByEnrollmentAndStatusKey(String enrollment, String statusKey);

        Optional<StudentModel> findByUser(UserModel user);
}
