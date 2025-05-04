package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClinicalHistoryCatalogRepository extends JpaRepository<ClinicalHistoryCatalogModel, Long> {
    @Query(value = "SELECT chc.id_clinical_history_catalog as id, chc.clinical_history_name, " +
            "pch.id_patient_clinical_history, pch.fk_patient as patientId " +
            "FROM clinical_history_catalogs chc " +
            "LEFT JOIN patient_clinical_histories pch " +
            "ON chc.id_clinical_history_catalog = pch.fk_clinical_history_catalog " +
            "AND pch.fk_patient = :patientId", nativeQuery = true)
    List<Object[]> findAllClinicalHistoryByPatientId(@Param("patientId") String patientId);

    Optional<ClinicalHistoryCatalogModel> findByClinicalHistoryName(String name);
}
