package edu.mx.unsis.unsiSmile.repository.periodontogram;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.mx.unsis.unsiSmile.model.periodontograms.PeriodontogramModel;

public interface IPeriodontogramRepository extends JpaRepository<PeriodontogramModel, Long> {

    @Query("select distinct p from PeriodontogramModel p " +
           "left join fetch p.toothEvaluations t " +
           "left join fetch t.surfaceEvaluations s " +
           "left join fetch s.surfaceMeasurements " +
           "where p.idPeriodontogram = :id")
    Optional<PeriodontogramModel> findByIdWithFetch(@Param("id") Long id);

    @Query("select distinct p from PeriodontogramModel p " +
           "left join fetch p.toothEvaluations t " +
           "left join fetch t.surfaceEvaluations s " +
           "left join fetch s.surfaceMeasurements " +
           "where p.patient.idPatient = :patientId and p.formSection.idFormSection = :formSectionId")
    Optional<PeriodontogramModel> findByPatientIdAndFormSectionId(@Param("patientId") Long patientId,
                                                                    @Param("formSectionId") Long formSectionId);

    //obtener el periodontograma mas reciente por id de paciente
    @Query("select distinct p from PeriodontogramModel p " +
           "left join fetch p.toothEvaluations t " +
           "left join fetch t.surfaceEvaluations s " +
           "left join fetch s.surfaceMeasurements " +
           "where p.patient.idPatient = :patientId " +
           "order by p.evaluationDate desc")
    Optional<PeriodontogramModel> findMostRecentByPatientId(@Param("patientId") Long patientId);
}
