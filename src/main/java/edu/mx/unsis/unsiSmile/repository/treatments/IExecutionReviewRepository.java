package edu.mx.unsis.unsiSmile.repository.treatments;

import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.treatments.ExecutionReviewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IExecutionReviewRepository extends JpaRepository<ExecutionReviewModel, Long> {

    Page<ExecutionReviewModel> findByProfessorClinicalArea_Professor_idProfessorAndStatus(
            String professorId, ReviewStatus status, Pageable pageable);

    Optional<ExecutionReviewModel> findTopByTreatmentDetail_IdTreatmentDetailOrderByIdExecutionReviewDesc(Long treatmentDetailId);

    @Query("SELECT COUNT(e) FROM ExecutionReviewModel e " +
            "WHERE e.professorClinicalArea.professor.idProfessor = :idProfessor " +
            "AND e.status = :status " +
            "AND e.statusKey = :statusKey")
    Long countByStatusAndProfessor(@Param("idProfessor") String idProfessor,
                                   @Param("status") ReviewStatus status,
                                   @Param("statusKey") String statusKey);
}