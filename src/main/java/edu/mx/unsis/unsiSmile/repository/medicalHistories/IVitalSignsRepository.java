package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.VitalSignsModel;

@Repository
public interface IVitalSignsRepository extends JpaRepository<VitalSignsModel, Long> {

}