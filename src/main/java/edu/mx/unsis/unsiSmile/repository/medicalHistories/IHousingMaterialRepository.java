package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.HousingMaterialModel;

public interface IHousingMaterialRepository extends JpaRepository<HousingMaterialModel, Long> {
    Optional<HousingMaterialModel> findByMaterial(String material);
}
