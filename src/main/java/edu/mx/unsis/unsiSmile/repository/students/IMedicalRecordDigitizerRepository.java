package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.MedicalRecordDigitizerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMedicalRecordDigitizerRepository extends JpaRepository<MedicalRecordDigitizerModel, Long> {

    @Query("SELECT m FROM MedicalRecordDigitizerModel m " +
            "WHERE m.endDate IS NULL AND m.student.person.firstName LIKE %:keyword% OR " +
            "m.student.person.secondName LIKE %:keyword% OR " +
            "m.student.person.firstLastName LIKE %:keyword% OR " +
            "m.student.person.secondLastName LIKE %:keyword% OR " +
            "m.student.enrollment LIKE %:keyword% ")
    Page<MedicalRecordDigitizerModel> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Optional<MedicalRecordDigitizerModel> findTopByStudent_EnrollmentOrderByCreatedAtDesc(String enrollment);

    @Query("SELECT m FROM MedicalRecordDigitizerModel m " +
            "WHERE m.endDate IS NULL")
    Page<MedicalRecordDigitizerModel> findAllMedicalRecordDigitizer(Pageable pageable);

}