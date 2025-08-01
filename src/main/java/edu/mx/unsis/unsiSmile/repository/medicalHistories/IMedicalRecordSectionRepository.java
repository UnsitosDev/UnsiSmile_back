package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.forms.sections.MedicalRecordSectionModel;
import edu.mx.unsis.unsiSmile.model.forms.sections.MedicalRecordSectionModelPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicalRecordSectionRepository extends JpaRepository<MedicalRecordSectionModel, MedicalRecordSectionModelPk> {
    @Query("SELECT mrs FROM MedicalRecordSectionModel mrs WHERE mrs.medicalRecordCatalogModel.idMedicalRecordCatalog = :medicalRecordId ORDER BY mrs.order")
    List<MedicalRecordSectionModel> findAllByMedicalRecordId(@Param("medicalRecordId") Long medicalRecordId);
}