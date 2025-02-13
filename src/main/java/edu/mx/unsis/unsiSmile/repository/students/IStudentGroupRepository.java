package edu.mx.unsis.unsiSmile.repository.students;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;

public interface IStudentGroupRepository extends JpaRepository<StudentGroupModel, Long> {
    
}
