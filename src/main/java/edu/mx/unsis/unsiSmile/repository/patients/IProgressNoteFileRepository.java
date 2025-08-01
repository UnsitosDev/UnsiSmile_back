package edu.mx.unsis.unsiSmile.repository.patients;

import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteFileModel;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProgressNoteFileRepository extends JpaRepository<ProgressNoteFileModel, String> {

    List<ProgressNoteFileModel> findByProgressNote(ProgressNoteModel progressNote);
}