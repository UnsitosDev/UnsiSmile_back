package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.SemesterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISemesterRepository extends JpaRepository<SemesterModel, Long> {

    @Query("SELECT s FROM SemesterModel s WHERE s.statusKey = 'A'")
    Optional<SemesterModel> findActiveSemester();

    @Modifying
    @Query("UPDATE SemesterModel s SET s.statusKey = 'I'")
    void disableAllSemesters();
}