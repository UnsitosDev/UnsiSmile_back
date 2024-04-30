package edu.mx.unsis.unsiSmile.repository.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.students.StudentSemesterModel;

@Repository
public interface IStudentSemesterRepository extends JpaRepository<StudentSemesterModel, Long> {
}
