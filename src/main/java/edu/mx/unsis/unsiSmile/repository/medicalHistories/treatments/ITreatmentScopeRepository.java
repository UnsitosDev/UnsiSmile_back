package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentScopeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITreatmentScopeRepository extends JpaRepository<TreatmentScopeModel, Long> {
    List<TreatmentScopeModel> findByScopeType_IdScopeType(Long scopeTypeId);
    boolean existsByScopeName(String scopeName);
}