package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ITreatmentDetailRepository extends JpaRepository<TreatmentDetailModel, Long> {
    Page<TreatmentDetailModel> findByPatientMedicalRecord_Patient_IdPatient(String patientId, Pageable pageable);

    @Query("SELECT COUNT(t) FROM TreatmentDetailModel t WHERE t.professor.idProfessor = ?1 AND t.status = ?2 AND t.statusKey = 'A'")
    Long countActiveTreatmentsByProfessorId(String professorId, ReviewStatus status);

    boolean existsByPatientMedicalRecord_Patient_idPatientAndStatus(String patientId, ReviewStatus status);

    Page<TreatmentDetailModel> findAllByStudentGroupIn(List<StudentGroupModel> studentGroup, Pageable pageable);

    Page<TreatmentDetailModel> findAllByStudentGroupInAndTreatment_IdTreatment(
            List<StudentGroupModel> studentGroups, Long idTreatment, Pageable pageable);

    Page<TreatmentDetailModel> findAllByProfessorAndStatus(
            ProfessorModel professor, ReviewStatus status, Pageable pageable);

    @Query("SELECT COUNT (*) FROM TreatmentDetailModel t WHERE t.studentGroup.student.enrollment = ?1 AND t.status = ?2 AND t.statusKey = 'A'")
    Long countByStudentAndStatus(String studentEnrollment, ReviewStatus status);

    @Query("SELECT t.treatment.name, COUNT(tt.idDetailTooth) FROM TreatmentDetailModel t " +
            "LEFT JOIN TreatmentDetailToothModel tt ON tt.treatmentDetail.idTreatmentDetail = t.idTreatmentDetail " +
            "WHERE t.studentGroup.student.enrollment = ?1 AND t.status = ?2 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name = 'Diente' " +
            "GROUP BY t.treatment.name")
    List<Object[]> countToothScopeTreatmentsByStudent(String enrollment, ReviewStatus status);

    @Query("SELECT t.treatment.name, COUNT(DISTINCT t.idTreatmentDetail) FROM TreatmentDetailModel t " +
            "WHERE t.studentGroup.student.enrollment = ?1 AND t.status = ?2 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name <> 'Diente' " +
            "GROUP BY t.treatment.name")
    List<Object[]> countGeneralScopeTreatmentsByStudent(String enrollment, ReviewStatus status);

    @Query("SELECT t.treatment.name, COUNT(tt.idDetailTooth) FROM TreatmentDetailModel t " +
            "LEFT JOIN TreatmentDetailToothModel tt ON tt.treatmentDetail.idTreatmentDetail = t.idTreatmentDetail " +
            "WHERE t.status = ?1 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name = 'Diente' " +
            "GROUP BY t.treatment.name")
    List<Object[]> countAllToothScopeTreatments(ReviewStatus status);

    @Query("SELECT t.treatment.name, COUNT(DISTINCT t.idTreatmentDetail) FROM TreatmentDetailModel t " +
            "WHERE t.status = ?1 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name <> 'Diente' " +
            "GROUP BY t.treatment.name")
    List<Object[]> countAllGeneralScopeTreatments(ReviewStatus status);

    Long countByStatusAndStatusKey(ReviewStatus status, String statusKey);

    List<TreatmentDetailModel> findByStudentGroup_StudentAndStatusAndStatusKey(StudentModel student, ReviewStatus status, String statusKey);

    @Query("SELECT t FROM TreatmentDetailModel t " +
            "WHERE t.studentGroup.student.enrollment = ?1 AND t.studentGroup.group.semester.idSemester = ?2 " +
            "AND t.status = ?3 AND t.statusKey = 'A' " +
            "ORDER BY t.treatment.name")
    List<TreatmentDetailModel> findByStudentAndSemester(String enrollment, Long semesterId, ReviewStatus status);

    Optional<TreatmentDetailModel> findByPatientMedicalRecord_IdPatientMedicalRecord(Long idPatientMedicalRecord);

    @Query("SELECT t.treatment.name, COUNT(tt.idDetailTooth) " +
            "FROM TreatmentDetailModel t " +
            "LEFT JOIN TreatmentDetailToothModel tt ON tt.treatmentDetail.idTreatmentDetail = t.idTreatmentDetail " +
            "WHERE t.status = ?1 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name = 'Diente' " +
            "AND t.createdAt BETWEEN ?2 AND ?3 " +
            "GROUP BY t.treatment.name")
    List<Object[]> countToothScopeTreatmentsBetweenDates(ReviewStatus status, Timestamp startDate, Timestamp endDate);

    @Query("SELECT t.treatment.name, COUNT(DISTINCT t.idTreatmentDetail) " +
            "FROM TreatmentDetailModel t " +
            "WHERE t.status = ?1 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name <> 'Diente' " +
            "AND t.createdAt BETWEEN ?2 AND ?3 " +
            "GROUP BY t.treatment.name")
    List<Object[]> countGeneralScopeTreatmentsBetweenDates(ReviewStatus status, Timestamp startDate, Timestamp endDate);

    @Query("SELECT td FROM TreatmentDetailModel td " +
            "WHERE td.patientMedicalRecord.patient.idPatient = :idPatient " +
            "AND td.patientMedicalRecord.medicalRecordCatalog.idMedicalRecordCatalog = :idMedicalRecordCatalog")
    List<TreatmentDetailModel> findUsedByPatientAndCatalog(
            @Param("idPatient") String idPatient,
            @Param("idMedicalRecordCatalog") Long idMedicalRecordCatalog);

    @Query("SELECT td FROM TreatmentDetailModel td WHERE td.patientMedicalRecord.patient.idPatient = :idPatient")
    List<TreatmentDetailModel> findByPatientId(@Param("idPatient") String idPatient);

    List<TreatmentDetailModel> findByStudentGroupInAndTreatment_IdTreatmentAndStatusOrderByIdTreatmentDetailDesc(
            List<StudentGroupModel> studentGroups,
            Long idTreatment,
            ReviewStatus status);

    @Query("SELECT t.treatment.name, COUNT(tt.idDetailTooth) " +
            "FROM TreatmentDetailModel t " +
            "LEFT JOIN TreatmentDetailToothModel tt ON tt.treatmentDetail.idTreatmentDetail = t.idTreatmentDetail " +
            "WHERE t.status = ?2 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name = 'Diente' " +
            "AND t.createdAt BETWEEN ?3 AND ?4 " +
            "AND t.studentGroup.student.enrollment = ?1 " +
            "GROUP BY t.treatment.name")
    List<Object[]> countToothScopeTreatmentsBetweenDatesByStudent(String enrollment, ReviewStatus status, Timestamp startDate, Timestamp endDate);

    @Query("SELECT t.treatment.name, COUNT(DISTINCT t.idTreatmentDetail) " +
            "FROM TreatmentDetailModel t " +
            "WHERE t.status = ?2 AND t.statusKey = 'A' AND t.treatment.treatmentScope.name <> 'Diente' " +
            "AND t.createdAt BETWEEN ?3 AND ?4 " +
            "AND t.studentGroup.student.enrollment = ?1 " +
            "GROUP BY t.treatment.name")
    List<Object[]> countGeneralScopeTreatmentsBetweenDatesByStudent(String enrollment, ReviewStatus status, Timestamp startDate, Timestamp endDate);
}