package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IGroupRepository extends JpaRepository<GroupModel, Long> {
    @Modifying
    @Query("UPDATE GroupModel s SET s.statusKey = 'I'")
    void disableAllGroups();
}
