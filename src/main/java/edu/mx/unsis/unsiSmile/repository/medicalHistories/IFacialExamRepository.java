package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialExamModel;

public interface IFacialExamRepository extends JpaRepository<FacialExamModel, Long> {
}
