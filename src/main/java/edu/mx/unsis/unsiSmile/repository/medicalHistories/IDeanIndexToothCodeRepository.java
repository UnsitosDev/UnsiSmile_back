package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis.DeanIndexModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis.DeanIndexToothCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeanIndexToothCodeRepository extends JpaRepository<DeanIndexToothCodeModel, Long> {
    List<DeanIndexToothCodeModel> findByDeanIndex(DeanIndexModel deanIndex);
}
