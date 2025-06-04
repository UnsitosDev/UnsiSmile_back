package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.AuthorizedTreatmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorizedTreatmentRepository extends JpaRepository<AuthorizedTreatmentModel, Long> {
    Page<AuthorizedTreatmentModel> findByProfessorClinicalArea_Professor_idProfessorAndIsAuthorized(
            String professorId, Boolean  isAuthorized, Pageable pageable);
}