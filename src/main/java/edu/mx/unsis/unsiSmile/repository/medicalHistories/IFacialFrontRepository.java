package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialFrontModel;

public interface IFacialFrontRepository extends JpaRepository<FacialFrontModel, Long> {
    Optional<FacialFrontModel> findByFacialFront(String facialFront);
}
