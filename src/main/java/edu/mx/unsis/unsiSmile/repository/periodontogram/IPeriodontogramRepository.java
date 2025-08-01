package edu.mx.unsis.unsiSmile.repository.periodontogram;

import edu.mx.unsis.unsiSmile.model.periodontograms.PeriodontogramModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPeriodontogramRepository extends JpaRepository<PeriodontogramModel, Long> {

    @Query("select distinct p from PeriodontogramModel p " +
            "left join p.toothEvaluations t " +
            "left join t.surfaceEvaluations s " +
            "left join s.surfaceMeasurements " +
            "where p.idPeriodontogram = :id")
    Optional<PeriodontogramModel> findByIdWithFetch(@Param("id") Long id);

    @Query("select distinct p from PeriodontogramModel p " +
            "left join p.toothEvaluations t " +
            "left join t.surfaceEvaluations s " +
            "left join s.surfaceMeasurements " +
            "where p.patient.idPatient = :patientId and p.formSection.idFormSection = :formSectionId")
    Optional<PeriodontogramModel> findByPatientIdAndFormSectionId(@Param("patientId") String patientId,
            @Param("formSectionId") Long formSectionId);

    // obtener el periodontograma mas reciente por id de paciente
    @Query("select distinct p from PeriodontogramModel p " +
            "left join p.toothEvaluations t " +
            "left join t.surfaceEvaluations s " +
            "left join s.surfaceMeasurements " +
            "where p.patient.idPatient = :patientId " +
            "order by p.evaluationDate desc")
    Optional<PeriodontogramModel> findMostRecentByPatientId(@Param("patientId") String patientId);
}