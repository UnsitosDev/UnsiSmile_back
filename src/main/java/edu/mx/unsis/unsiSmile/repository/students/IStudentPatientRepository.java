package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.StudentPatientModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IStudentPatientRepository extends JpaRepository<StudentPatientModel, Long> {

    Optional<StudentPatientModel> findByIdStudentPatient(Long idStudentPatient);

    Optional<StudentPatientModel> findByPatient(PatientModel patient);

    Optional<StudentPatientModel> findByStudent(StudentModel student);

    List<StudentPatientModel> findAllByStudentEnrollment(String studentId);

    @Query("SELECT sp FROM StudentPatientModel sp where sp.patient.idPatient IN :patientsId AND" +
            " sp.statusKey='A' AND sp.student.statusKey = 'A'")
    List<StudentPatientModel> findAllByPatientsId(@Param("patientsId") Set<String> patientsId);

    @Query("SELECT p FROM StudentPatientModel p " +
            "JOIN p.patient patient " +
            "JOIN patient.person person " +
            "JOIN patient.address address " +
            "WHERE (person.curp LIKE %:keyword% " +
            "OR person.firstName LIKE %:keyword% " +
            "OR person.secondName LIKE %:keyword% " +
            "OR person.firstLastName LIKE %:keyword% " +
            "OR person.secondLastName LIKE %:keyword% " +
            "OR address.street.name LIKE %:keyword%) " +
            "AND p.student.enrollment = :enrollment " +
            "AND p.statusKey = 'A' " +
            "AND patient.statusKey = 'A' " +
            "ORDER BY person.firstName ASC")
    List<StudentPatientModel> findAllBySearchInput(String enrollment, String keyword, Pageable pageable);
}