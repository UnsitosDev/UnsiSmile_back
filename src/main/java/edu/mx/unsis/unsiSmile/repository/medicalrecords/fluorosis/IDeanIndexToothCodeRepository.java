package edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.DeanIndexModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.DeanIndexToothCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeanIndexToothCodeRepository extends JpaRepository<DeanIndexToothCodeModel, Long> {

    List<DeanIndexToothCodeModel> findByDeanIndex(DeanIndexModel deanIndex);
}