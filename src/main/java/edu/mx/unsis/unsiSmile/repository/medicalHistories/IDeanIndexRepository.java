package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis.DeanIndexModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDeanIndexRepository extends JpaRepository<DeanIndexModel, Long> {
    Optional<DeanIndexModel> findByTreatmentDetail_IdTreatmentDetail(Long idTreatmentDetail);
}