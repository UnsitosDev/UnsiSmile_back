package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITreatmentDetailRepository extends JpaRepository<TreatmentDetailModel, Long> {
    Page<TreatmentDetailModel> findByPatientClinicalHistory_Patient_IdPatient(String patientId, Pageable pageable);
}
