package edu.mx.unsis.unsiSmile.repository.students;

import edu.mx.unsis.unsiSmile.model.students.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
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
            "WHERE (m.student.person.firstName LIKE %:keyword% OR " +
            "m.student.person.secondName LIKE %:keyword% OR " +
            "m.student.person.firstLastName LIKE %:keyword%) AND m.statusKey = 'A'")
    Page<MedicalRecordDigitizerModel> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByStudentAndStatusKey(StudentModel student, String statusKey);

    Page<MedicalRecordDigitizerModel> findAllByStatusKey(String statusKey, Pageable pageable);
}