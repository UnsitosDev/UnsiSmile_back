package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<StudentModel, String> {

        Optional<StudentModel> findByEnrollmentAndStatusKey(String enrollment, String statusKey);

        Optional<StudentModel> findByUser(UserModel user);

        @Query("SELECT s FROM StudentModel s WHERE s.enrollment LIKE %:keyword% " +
                "OR s.person.curp LIKE %:keyword% " +
                "OR s.person.firstName LIKE %:keyword% " +
                "OR s.person.secondName LIKE %:keyword% " +
                "OR s.person.firstLastName LIKE %:keyword% " +
                "OR s.person.secondLastName LIKE %:keyword% " +
                "OR s.person.phone LIKE %:keyword% " +
                "OR s.person.email LIKE %:keyword% " +
                "OR s.person.gender.gender LIKE %:keyword% " +
                "OR (YEAR(s.person.birthDate) = :keywordInt " +
                "OR MONTH(s.person.birthDate) = :keywordInt " +
                "OR DAY(s.person.birthDate) = :keywordInt) " +
                "AND s.statusKey='A'")
        Page<StudentModel> findAllBySearchInput(
                @Param("keyword") String keyword,
                @Param("keywordInt") Integer keywordInt,
                Pageable pageable);

        @Modifying
        @Query("UPDATE StudentModel s SET s.statusKey = 'I'")
        void disableAllStudents();

        @Modifying
        @Query("UPDATE StudentModel s SET s.statusKey = 'A' WHERE s.enrollment = :enrollment")
        void enableStudent(@Param("enrollment") String enrollment);
}
