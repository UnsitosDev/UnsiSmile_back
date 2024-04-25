package edu.mx.unsis.unsiSmile.repository.students;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.students.CycleModel;

public interface ICycleRepository extends JpaRepository<CycleModel,Long> {
}
