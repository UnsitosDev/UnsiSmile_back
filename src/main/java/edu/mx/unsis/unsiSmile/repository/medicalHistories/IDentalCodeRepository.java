package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.DentalCodeModel;

public interface IDentalCodeRepository extends JpaRepository<DentalCodeModel, Long> {
    Optional<DentalCodeModel> findByCode(String code);
}
