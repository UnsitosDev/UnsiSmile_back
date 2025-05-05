package edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITreatmentDetailRepository extends JpaRepository<TreatmentDetailModel, Long> {
    Page<TreatmentDetailModel> findByPatientClinicalHistory_Patient_IdPatient(String patientId, Pageable pageable);

    @Query("SELECT COUNT(t) FROM TreatmentDetailModel t WHERE t.professor.idProfessor = ?1 AND t.status = ?2 AND t.statusKey = 'A'")
    Long countActiveTreatmentsByProfessorId(String professorId, String status);

    boolean existsByPatientClinicalHistory_Patient_idPatientAndStatus(String patientId, String status);

    Page<TreatmentDetailModel> findAllByStudentGroupIn(List<StudentGroupModel> studentGroup, Pageable pageable);

    Page<TreatmentDetailModel> findAllByStudentGroupInAndTreatment_IdTreatment(
            List<StudentGroupModel> studentGroups, Long idTreatment, Pageable pageable);

    Page<TreatmentDetailModel> findAllByProfessorAndStatus(
            ProfessorModel professor, String status, Pageable pageable);
}