package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClinicalHistoryCatalogRepository extends JpaRepository<ClinicalHistoryCatalogModel, Long> {
}
