package edu.mx.unsis.unsiSmile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.CareerModel;

public interface ICareerRepository extends JpaRepository<CareerModel, Long> {
    Optional<CareerModel> findByCareer(String career);
}