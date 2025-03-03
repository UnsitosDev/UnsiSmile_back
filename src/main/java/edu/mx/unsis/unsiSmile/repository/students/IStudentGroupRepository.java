package edu.mx.unsis.unsiSmile.repository.students;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
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
}
