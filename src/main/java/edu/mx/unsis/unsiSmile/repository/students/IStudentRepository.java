package edu.mx.unsis.unsiSmile.repository.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.students.StudentModel;

@Repository
public interface IStudentRepository extends JpaRepository<StudentModel, String> {
}
