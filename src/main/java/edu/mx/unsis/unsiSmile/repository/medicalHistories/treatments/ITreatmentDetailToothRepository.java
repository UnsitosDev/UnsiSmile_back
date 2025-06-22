package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailToothModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITreatmentDetailToothRepository extends JpaRepository<TreatmentDetailToothModel, Long> {
    List<TreatmentDetailToothModel> findByTreatmentDetail_IdTreatmentDetail(Long treatmentDetailId);
    void deleteByTreatmentDetail_IdTreatmentDetail(Long treatmentDetailId);

    List<TreatmentDetailToothModel> findByTreatmentDetail_IdTreatmentDetailAndTooth_IdToothIn(
            Long treatmentDetailId, List<String> toothCodes
    );

    Optional<TreatmentDetailToothModel> findByTreatmentDetail_IdTreatmentDetailAndTooth_IdTooth(
            Long treatmentDetailId, String toothCode
    );

    List<TreatmentDetailToothModel> findByTreatmentDetail_IdTreatmentDetailAndInReviewTrue(
            Long treatmentDetailId
    );
}