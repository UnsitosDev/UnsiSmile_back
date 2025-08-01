package edu.mx.unsis.unsiSmile.repository.treatments;

import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.treatments.AuthorizedTreatmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthorizedTreatmentRepository extends JpaRepository<AuthorizedTreatmentModel, Long> {

    Page<AuthorizedTreatmentModel> findByProfessorClinicalArea_Professor_idProfessorAndStatus(
            String professorId, ReviewStatus status, Pageable pageable);

    Page<AuthorizedTreatmentModel> findByProfessorClinicalArea_Professor_idProfessor(
            String professorId, Pageable pageable);

    Optional<AuthorizedTreatmentModel> findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(Long treatmentDetailId);

    @Query("SELECT COUNT(a) FROM AuthorizedTreatmentModel a " +
            "WHERE a.professorClinicalArea.professor.idProfessor = :idProfessor " +
            "AND a.status = :status " +
            "AND a.statusKey = :statusKey")
    Long countByStatusAndProfessor(@Param("idProfessor") String idProfessor,
                       @Param("status") ReviewStatus status,
                       @Param("statusKey") String statusKey);
}