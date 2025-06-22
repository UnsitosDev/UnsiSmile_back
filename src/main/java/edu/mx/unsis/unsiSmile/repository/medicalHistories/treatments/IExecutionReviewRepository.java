package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ExecutionReviewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IExecutionReviewRepository extends JpaRepository<ExecutionReviewModel, Long> {
    Page<ExecutionReviewModel> findByProfessorClinicalArea_Professor_idProfessorAndStatus(
            String professorId, ReviewStatus status, Pageable pageable);

    Optional<ExecutionReviewModel> findTopByTreatmentDetail_IdTreatmentDetailOrderByIdExecutionReviewDesc(Long treatmentDetailId);
}