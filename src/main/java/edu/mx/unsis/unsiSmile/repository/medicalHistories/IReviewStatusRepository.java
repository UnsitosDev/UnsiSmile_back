package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatusModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewStatusRepository extends JpaRepository<ReviewStatusModel, Long> {

    Optional<ReviewStatusModel> findByPatientClinicalHistory_IdPatientClinicalHistoryAndFormSection_IdFormSection(
            Long idPatientClinicalHistory, Long idSection);

    @Query("SELECT s FROM ReviewStatusModel s " +
            "WHERE s.patientClinicalHistory.patient.idPatient = :idPatient " +
            "AND s.formSection.idFormSection = :idSection " +
            "ORDER BY s.createdAt DESC")
    List<ReviewStatusModel> findAllByPatientIdAndSectionOrdered(
            @Param("idPatient") String idPatient,
            @Param("idSection") Long idSection);

    @Query("SELECT DISTINCT s FROM ReviewStatusModel s " +
            "WHERE s.status = :status " +
            "GROUP BY s.patientClinicalHistory.patient.idPatient")
    Page<ReviewStatusModel> findByStatus(@Param("status") ReviewStatus status, Pageable pageable);

    @Query("SELECT DISTINCT s FROM ReviewStatusModel s " +
            "WHERE s.patientClinicalHistory.patient.idPatient = :idPatient " +
            "AND s.status = :status" +
            " GROUP BY s.patientClinicalHistory.idPatientClinicalHistory")
    List<ReviewStatusModel> findByIdPatientAndStatus(@Param("idPatient") String idPatient,
                                                              @Param("status") ReviewStatus status);

    @Query("SELECT s FROM ReviewStatusModel s " +
            "WHERE s.status = :status " +
            "AND s.professorClinicalArea.professor.idProfessor = :professorId")
    Page<ReviewStatusModel> findByStatusAndProfessor(@Param("professorId") String professorId,
                                                     @Param("status") ReviewStatus status,
                                                     Pageable pageable);

    @Query("SELECT COUNT(r) FROM ReviewStatusModel r " +
            "WHERE r.professorClinicalArea.professor.idProfessor = :idProfessor " +
            "AND r.status = :status " +
            "AND r.statusKey = :statusKey")
    Long countByStatus(@Param("idProfessor") String idProfessor,
                       @Param("status") ReviewStatus status,
                       @Param("statusKey") String statusKey);

    boolean existsByPatientClinicalHistory_IdPatientClinicalHistoryAndStatus(
            Long idPatientMedicalRecord, ReviewStatus status);
}