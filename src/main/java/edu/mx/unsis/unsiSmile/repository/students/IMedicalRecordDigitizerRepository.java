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
            "WHERE m.statusKey != :deleted AND m.user.username LIKE %:keyword%")
    Page<MedicalRecordDigitizerModel> searchByKeyword(@Param("keyword") String keyword,
                                                      @Param("deleted") String deleted,
                                                      Pageable pageable);

    Optional<MedicalRecordDigitizerModel> findTopByUser_UsernameOrderByCreatedAtDesc(String username);

    @Query("SELECT m FROM MedicalRecordDigitizerModel m WHERE m.statusKey != :deleted")
    Page<MedicalRecordDigitizerModel> findAllMedicalRecordDigitizer(@Param("deleted") String deleted, Pageable pageable);
}