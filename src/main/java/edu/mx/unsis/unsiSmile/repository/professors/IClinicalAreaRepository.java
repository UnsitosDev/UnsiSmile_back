package edu.mx.unsis.unsiSmile.repository.professors;

import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClinicalAreaRepository extends JpaRepository<ClinicalAreaModel, Long> {

    @Query("SELECT c FROM ClinicalAreaModel c WHERE c.clinicalArea LIKE %:keyword% AND c.statusKey = :statusKey")
    Page<ClinicalAreaModel> findAllBySearchInput(@Param("keyword") String keyword, @Param("statusKey") String statusKey, Pageable pageable);

    @Query("SELECT c FROM ClinicalAreaModel c WHERE c.statusKey = :statusKey")
    Page<ClinicalAreaModel> findAllByStatusKey(@Param("statusKey") String statusKey, Pageable pageable);

    Optional<ClinicalAreaModel> findByIdClinicalAreaAndStatusKey(Long id, String statusKey);
}