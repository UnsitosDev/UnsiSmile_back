package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.MedicalRecordCatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicalRecordCatalogRepository extends JpaRepository<MedicalRecordCatalogModel, Long> {
    @Query(value = "SELECT mrc.id_medical_record_catalog as id, mrc.medical_record_name, " +
            "pmr.id_patient_medical_record, pmr.fk_patient as patientId " +
            "FROM medical_record_catalogs mrc " +
            "LEFT JOIN patient_medical_records pmr " +
            "ON mrc.id_medical_record_catalog = pmr.fk_medical_record_catalog " +
            "AND pmr.fk_patient = :patientId", nativeQuery = true)
    List<Object[]> findAllMedicalRecordByPatientId(@Param("patientId") String patientId);

    Optional<MedicalRecordCatalogModel> findByMedicalRecordName(String name);
}