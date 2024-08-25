package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.OdontogramModel;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontogramRepository extends JpaRepository<OdontogramModel, Long> {
}
