package edu.mx.unsis.unsiSmile.repository.students;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.StudentPatientModel;

@Repository
public interface IStudentPatientRepository extends JpaRepository<StudentPatientModel, Long> {

    Optional<StudentPatientModel> findByIdStudentPatient(Long idStudentPatient);

    Optional<StudentPatientModel> findByPatient(PatientModel patient);

    Optional<StudentPatientModel> findByStudent(StudentModel student);

}