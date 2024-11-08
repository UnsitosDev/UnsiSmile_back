package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<StudentModel, String> {

        Optional<StudentModel> findByEnrollmentAndStatusKey(String enrollment, String statusKey);

        Optional<StudentModel> findByUser(UserModel user);

        @Query("SELECT s FROM StudentModel s WHERE s.enrollment LIKE %:keyWord% " +
                "OR s.person.curp LIKE %:keyWord% " +
                "OR s.person.firstName LIKE %:keyWord% " +
                "OR s.person.secondName LIKE %:keyWord% " +
                "OR s.person.firstLastName LIKE %:keyWord% " +
                "OR s.person.secondLastName LIKE %:keyWord% " +
                "OR s.person.phone LIKE %:keyWord% " +
                "OR s.person.email LIKE %:keyWord% " +
                "OR s.person.gender.gender LIKE %:keyWord% " +
                "OR (YEAR(s.person.birthDate) = :keyWordInt " +
                "OR MONTH(s.person.birthDate) = :keyWordInt " +
                "OR DAY(s.person.birthDate) = :keyWord) " +
                "AND s.statusKey='A'")
        Page<StudentModel> findAllBySearchInput(
                @Param("keyWord") String keyWord,
                @Param("keyWordInt") Integer keyWordInt,
                Pageable pageable);
}
