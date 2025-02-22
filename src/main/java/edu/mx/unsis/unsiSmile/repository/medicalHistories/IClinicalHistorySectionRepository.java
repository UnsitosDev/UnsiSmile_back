package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;
import edu.mx.unsis.unsiSmile.model.utils.ClinicalHistorySectionModelPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClinicalHistorySectionRepository extends JpaRepository<ClinicalHistorySectionModel, ClinicalHistorySectionModelPk> {
    @Query("SELECT chs FROM ClinicalHistorySectionModel chs WHERE chs.clinicalHistoryCatalogModel.idClinicalHistoryCatalog = :clinicalHistoryId")
    List<ClinicalHistorySectionModel> findAllByClinicalHistoryId(@Param("clinicalHistoryId") Long clinicalHistoryId);
}
