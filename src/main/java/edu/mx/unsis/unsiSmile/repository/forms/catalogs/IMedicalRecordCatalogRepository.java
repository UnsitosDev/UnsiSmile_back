package edu.mx.unsis.unsiSmile.repository.forms.catalogs;

import edu.mx.unsis.unsiSmile.model.forms.catalogs.MedicalRecordCatalogModel;
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

    @Query(value = "SELECT mrc.id_medical_record_catalog as id, mrc.medical_record_name, " +
            "pmr.id_patient_medical_record, pmr.fk_patient as patientId " +
            "FROM medical_record_catalogs mrc " +
            "LEFT JOIN patient_medical_records pmr " +
            "ON mrc.id_medical_record_catalog = pmr.fk_medical_record_catalog " +
            "AND pmr.fk_patient = :patientId " +
            "AND (pmr.id_patient_medical_record IS NULL OR pmr.id_patient_medical_record NOT IN :usedPmrIds)",
            nativeQuery = true)
    List<Object[]> findAllMedicalRecordByPatientId(
            @Param("patientId") String patientId,
            @Param("usedPmrIds") List<Long> usedPmrIds);
}