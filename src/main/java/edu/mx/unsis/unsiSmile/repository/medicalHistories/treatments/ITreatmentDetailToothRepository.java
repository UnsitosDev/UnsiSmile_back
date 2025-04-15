package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailToothModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITreatmentDetailToothRepository extends JpaRepository<TreatmentDetailToothModel, Long> {
    List<TreatmentDetailToothModel> findByTreatmentDetail_IdTreatmentDetail(Long treatmentDetailId);
    void deleteByTreatmentDetail_IdTreatmentDetail(Long treatmentDetailId);
}