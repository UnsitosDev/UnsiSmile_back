package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentScopeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITreatmentScopeRepository extends JpaRepository<TreatmentScopeModel, Long> {
    boolean existsByName(String name);
}