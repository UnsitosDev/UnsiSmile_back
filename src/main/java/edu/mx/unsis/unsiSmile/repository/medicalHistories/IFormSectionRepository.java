package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFormSectionRepository extends JpaRepository<FormSectionModel, Long> {

    @Query("SELECT fs FROM FormSectionModel fs WHERE fs.parentSection.idFormSection = :parentSectionId")
    List<FormSectionModel> findByParenSectionId(@Param("parentSectionId") Long parentSectionId);
}
