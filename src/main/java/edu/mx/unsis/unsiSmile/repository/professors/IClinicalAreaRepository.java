package edu.mx.unsis.unsiSmile.repository.professors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;

@Repository
public interface IClinicalAreaRepository extends JpaRepository<ClinicalAreaModel, Long> {
    @Query("SELECT c FROM ClinicalAreaModel c WHERE c.clinicalArea LIKE %:keyword%")
    Page<ClinicalAreaModel> findAllBySearchInput(String keyword, Pageable pageable);
}
