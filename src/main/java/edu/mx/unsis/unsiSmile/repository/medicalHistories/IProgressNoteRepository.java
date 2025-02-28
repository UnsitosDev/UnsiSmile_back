package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProgressNoteRepository extends JpaRepository<ProgressNoteModel, String> {
    Page<ProgressNoteModel> findByPatient(PatientModel patient, Pageable pageable);
}