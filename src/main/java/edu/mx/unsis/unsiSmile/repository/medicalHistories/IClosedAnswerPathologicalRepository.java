package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ClosedAnswerPathologicalModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;

public interface IClosedAnswerPathologicalRepository extends JpaRepository<ClosedAnswerPathologicalModel, Long> {
    List<ClosedAnswerPathologicalModel> findByMedicalHistory(MedicalHistoryModel medicalHistory);
}
