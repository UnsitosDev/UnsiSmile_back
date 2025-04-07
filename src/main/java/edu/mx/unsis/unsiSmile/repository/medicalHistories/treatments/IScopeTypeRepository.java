package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ScopeTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScopeTypeRepository extends JpaRepository<ScopeTypeModel, Long> {
    boolean existsByName(String name);
}
