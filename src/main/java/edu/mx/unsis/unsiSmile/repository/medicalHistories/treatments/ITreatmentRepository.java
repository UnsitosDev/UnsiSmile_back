package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.treatments.TreatmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITreatmentRepository extends JpaRepository<TreatmentModel, Long> {
    List<TreatmentModel> findByTreatmentScope_IdTreatmentScope(Long treatmentScopeId);
    List<TreatmentModel> findByMedicalRecordCatalog_IdMedicalRecordCatalog(Long catalogId);
    boolean existsByName(String name);
}