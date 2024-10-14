package edu.mx.unsis.unsiSmile.repository.students;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.StudentPatientModel;

@Repository
public interface IStudentPatientRepository extends JpaRepository<StudentPatientModel, Long> {

    Optional<StudentPatientModel> findByIdStudentPatient(Long idStudentPatient);

    Optional<StudentPatientModel> findByPatient(PatientModel patient);

    Optional<StudentPatientModel> findByStudent(StudentModel student);

    List<StudentPatientModel> findAllByStudentEnrollment(String studentId);

    @Query("SELECT sp FROM StudentPatientModel sp where sp.patient.idPatient IN :patientsId AND" +
            " sp.statusKey='A' AND sp.student.statusKey = 'A'")
    List<StudentPatientModel> findAllByPatientsId(@Param("patientsId") Set<Long> patientsId);
}