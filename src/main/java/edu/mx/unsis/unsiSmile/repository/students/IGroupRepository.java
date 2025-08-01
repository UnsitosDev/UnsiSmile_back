package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.model.students.SemesterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IGroupRepository extends JpaRepository<GroupModel, Long> {

    @Modifying
    @Query("UPDATE GroupModel s SET s.statusKey = 'I'")
    void disableAllGroups();

    @Query("SELECT g FROM GroupModel g WHERE g.groupName = :groupName AND g.semesterNumber = :semesterNumber AND g.career = :career AND g.semester = :semester")
    Optional<GroupModel> findByGroupNameAndSemesterNumberAndCareerAndSemester(
            @Param("groupName") String groupName,
            @Param("semesterNumber") String semesterNumber,
            @Param("career") CareerModel career,
            @Param("semester") SemesterModel semester);

    @Query("SELECT g FROM GroupModel g WHERE g.career.idCareer = :careerId AND g.statusKey = 'A'")
    List<GroupModel> findByCareerId(@Param("careerId") String careerId);
}