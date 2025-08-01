package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalrecords.SOHIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISOHIRepository extends JpaRepository<SOHIModel, Long> {
    Optional<SOHIModel> findByTreatmentDetail_IdTreatmentDetail(Long idTreatmentDetail);
}