package edu.mx.unsis.unsiSmile.repository.files;

import edu.mx.unsis.unsiSmile.model.files.GeneralFileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGeneralFileRepository extends JpaRepository<GeneralFileModel, String> {
    List<GeneralFileModel> findAllByStatusKey(String statusKey);
}