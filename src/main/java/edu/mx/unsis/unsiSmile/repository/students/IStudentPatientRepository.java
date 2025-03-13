package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.StudentPatientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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

    @Query("SELECT COUNT(sp) FROM StudentPatientModel sp WHERE sp.student.enrollment = :studentId AND " +
            "sp.student.statusKey = :status")
    Long countPatientsForStudent(@Param("studentId") String studentId, @Param("status") String status);

    @Query("SELECT COUNT(sp) FROM StudentPatientModel sp WHERE sp.student.enrollment = :studentId AND " +
            "sp.student.statusKey = :status AND sp.patient.hasDisability = true")
    Long countPatientsWithDisabilityByStudent(@Param("studentId") String studentId, @Param("status") String status);

    @Query("SELECT COUNT(sp) FROM StudentPatientModel sp WHERE sp.student.enrollment = :studentId AND " +
            "sp.student.statusKey = :status AND sp.patient.createdAt >= :lastMonth")
    Long countPatientsRegisteredSinceByStudent(@Param("studentId") String studentId,
                                               @Param("lastMonth") Timestamp lastMonth,
                                               @Param("status") String status);

    @Query("SELECT sp.patient.nationality.nationality, COUNT(sp) FROM StudentPatientModel sp WHERE " +
            "sp.student.enrollment = :studentId AND sp.student.statusKey = :status GROUP BY sp.patient.nationality")
    List<Object[]> countPatientsByNationalityByStudent(@Param("studentId") String studentId,
                                                       @Param("status") String status);

    @Query("SELECT sp FROM StudentPatientModel sp WHERE sp.patient.idPatient = :patientId")
    Page<StudentPatientModel> findByPatientId(@Param("patientId")  String patientId, Pageable pageable);

    @Query("SELECT COUNT(sp) FROM StudentPatientModel sp " +
            "WHERE sp.student.enrollment = :studentId " +
            "AND sp.student.statusKey = :status " +
            "AND FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', sp.patient.person.birthDate) < 18")
    Long countPatientsUnder18ByStudent(@Param("studentId") String studentId, @Param("status") String status);

    @Query("SELECT COUNT(sp) FROM StudentPatientModel sp " +
            "WHERE sp.student.enrollment = :studentId " +
            "AND sp.student.statusKey = :status " +
            "AND FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', sp.patient.person.birthDate) BETWEEN 18 AND 60")
    Long countPatientsBetween18And60ByStudent(@Param("studentId") String studentId, @Param("status") String status);

    @Query("SELECT COUNT(sp) FROM StudentPatientModel sp " +
            "WHERE sp.student.enrollment = :studentId " +
            "AND sp.student.statusKey = :status " +
            "AND FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', sp.patient.person.birthDate) > 60")
    Long countPatientsOver60ByStudent(@Param("studentId") String studentId, @Param("status") String status);
}