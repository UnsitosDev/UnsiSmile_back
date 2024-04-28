package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OpenAnswerPathologicalModel;

public interface IOpenAnswerPathologicalRepository extends JpaRepository<OpenAnswerPathologicalModel, Long> {
    List<OpenAnswerPathologicalModel> findByMedicalHistory(MedicalHistoryModel medicalHistory);
}
