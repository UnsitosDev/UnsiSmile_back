package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface IStudentGroupRepository extends JpaRepository<StudentGroupModel, Long> {
    @Query("SELECT COUNT(s) FROM StudentGroupModel s WHERE s.group.idGroup IN :groupIds")
    Long countStudentsByGroupIds(@Param("groupIds") Set<Long> groupIds);

    @Query("SELECT sg FROM StudentGroupModel sg WHERE " +
            "sg.student.enrollment = :enrollment AND " +
            "sg.group.idGroup = :groupId AND sg.statusKey = 'A'")
    Optional<StudentGroupModel> findByStudentAndGroup(@Param("enrollment") String enrollment,
                                                      @Param("groupId") Long groupId);

    @Modifying
    @Query("UPDATE StudentGroupModel s SET s.statusKey = 'I' WHERE s.student.enrollment = :enrollment " +
            "AND s.idStudentGroups = (SELECT MAX(s2.idStudentGroups) FROM StudentGroupModel s2 " +
            "WHERE s2.student.enrollment = :enrollment)")
    void disableLatestStudentGroup(@Param("enrollment") String enrollment);

    @Query("SELECT sg FROM StudentGroupModel sg WHERE sg.statusKey = 'A'")
    Page<StudentGroupModel> findAllActive(Pageable pageable);

    @Query("SELECT sg FROM StudentGroupModel sg WHERE " +
            "(sg.student.enrollment LIKE %:keyword% " +
            "OR sg.student.person.curp LIKE %:keyword% " +
            "OR sg.student.person.firstName LIKE %:keyword% " +
            "OR sg.student.person.secondName LIKE %:keyword% " +
            "OR sg.student.person.firstLastName LIKE %:keyword% " +
            "OR sg.student.person.secondLastName LIKE %:keyword% " +
            "OR sg.student.person.phone LIKE %:keyword% " +
            "OR sg.student.person.email LIKE %:keyword% " +
            "OR sg.student.person.gender.gender LIKE %:keyword% " +
            "OR (YEAR(sg.student.person.birthDate) = :keywordInt " +
            "OR MONTH(sg.student.person.birthDate) = :keywordInt " +
            "OR DAY(sg.student.person.birthDate) = :keywordInt)) " +
            "AND sg.statusKey = 'A'")
    Page<StudentGroupModel> findAllBySearchInput(
            @Param("keyword") String keyword,
            @Param("keywordInt") Integer keywordInt,
            Pageable pageable);

    @Query("SELECT sg FROM StudentGroupModel sg " +
            "WHERE sg.student.enrollment = :enrollment " +
            "AND sg.statusKey = 'A' " +
            "ORDER BY sg.idStudentGroups DESC LIMIT 1")
    Optional<StudentGroupModel> findLatestActiveByStudent(@Param("enrollment") String enrollment);
}
