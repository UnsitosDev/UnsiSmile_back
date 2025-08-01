package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.CycleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICycleRepository extends JpaRepository<CycleModel,Long> {
}