package edu.mx.unsis.unsiSmile.repository.students;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.students.CareerModel;

public interface ICareerRepository extends JpaRepository<CareerModel, String> {
    Optional<CareerModel> findByCareer(String career);
}