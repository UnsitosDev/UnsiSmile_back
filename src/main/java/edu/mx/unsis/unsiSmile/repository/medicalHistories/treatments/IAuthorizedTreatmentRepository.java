package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.AuthorizedTreatmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthorizedTreatmentRepository extends JpaRepository<AuthorizedTreatmentModel, Long> {
    Page<AuthorizedTreatmentModel> findByProfessorClinicalArea_Professor_idProfessorAndStatus(
            String professorId, String  status, Pageable pageable);

    Page<AuthorizedTreatmentModel> findByProfessorClinicalArea_Professor_idProfessor(
            String professorId, Pageable pageable);

    Optional<AuthorizedTreatmentModel> findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(Long treatmentDetailId);
}