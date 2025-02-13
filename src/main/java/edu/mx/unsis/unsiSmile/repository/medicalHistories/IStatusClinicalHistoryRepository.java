package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.StatusClinicalHistoryModel;

@Repository
public interface IStatusClinicalHistoryRepository extends JpaRepository<StatusClinicalHistoryModel, Long> {

    Optional<StatusClinicalHistoryModel> findByPatientClinicalHistory_IdPatientClinicalHistory(Long idPatientClinicalHistory);
    
}
