package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProgressNoteRepository extends JpaRepository<ProgressNoteModel, String> {
}