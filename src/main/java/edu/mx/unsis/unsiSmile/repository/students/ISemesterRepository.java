package edu.mx.unsis.unsiSmile.repository.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.students.SemesterModel;

@Repository
public interface ISemesterRepository extends JpaRepository<SemesterModel, Long> {
}
