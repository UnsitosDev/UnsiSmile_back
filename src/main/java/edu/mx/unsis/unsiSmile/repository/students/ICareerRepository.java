package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICareerRepository extends JpaRepository<CareerModel, String> {

    Optional<CareerModel> findByCareer(String career);
}