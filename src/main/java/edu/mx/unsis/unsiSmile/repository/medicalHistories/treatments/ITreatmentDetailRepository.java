package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITreatmentDetailRepository extends JpaRepository<TreatmentDetailModel, Long> {
    List<TreatmentDetailModel> findByPatientClinicalHistory_IdPatientClinicalHistory(Long patientClinicalHistoryId);
    List<TreatmentDetailModel> findByTreatment_IdTreatment(Long treatmentId);
    List<TreatmentDetailModel> findByProfessor_IdProfessor(String professorId);
}
