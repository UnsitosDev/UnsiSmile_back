package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ClinicalHistoryStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.StatusClinicalHistoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStatusClinicalHistoryRepository extends JpaRepository<StatusClinicalHistoryModel, Long> {

    Optional<StatusClinicalHistoryModel> findByPatientClinicalHistory_IdPatientClinicalHistoryAndFormSection_IdFormSection(
            Long idPatientClinicalHistory, Long idSection);

    Page<StatusClinicalHistoryModel> findByStatus(ClinicalHistoryStatus status, Pageable pageable);
}
