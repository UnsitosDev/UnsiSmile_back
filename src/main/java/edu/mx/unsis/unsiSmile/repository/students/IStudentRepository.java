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

        @Query("SELECT s FROM StudentModel s WHERE s.enrollment LIKE %:searchInput% " +
                "OR s.person.curp LIKE %:searchInput% " +
                "OR s.person.firstName LIKE %:searchInput% " +
                "OR s.person.secondName LIKE %:searchInput% " +
                "OR s.person.firstLastName LIKE %:searchInput% " +
                "OR s.person.secondLastName LIKE %:searchInput% " +
                "OR s.person.phone LIKE %:searchInput% " +
                "OR s.person.email LIKE %:searchInput% " +
                "OR s.person.gender.gender LIKE %:searchInput% " +
                "OR (YEAR(s.person.birthDate) = :searchInputInt " +
                "OR MONTH(s.person.birthDate) = :searchInputInt " +
                "OR DAY(s.person.birthDate) = :searchInputInt) " +
                "AND s.statusKey='A'")
        Page<StudentModel> findAllBySearchInput(
                @Param("searchInput") String searchInput,
                @Param("searchInputInt") Integer searchInputInt,
                Pageable pageable);
}
