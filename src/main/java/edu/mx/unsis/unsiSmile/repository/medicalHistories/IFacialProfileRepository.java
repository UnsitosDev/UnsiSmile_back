package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialProfileModel;

public interface IFacialProfileRepository extends JpaRepository<FacialProfileModel, Long> {
    Optional<FacialProfileModel> findByFacialProfile(String facialProfile);
}