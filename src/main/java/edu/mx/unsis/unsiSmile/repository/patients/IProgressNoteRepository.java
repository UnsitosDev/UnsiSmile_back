package edu.mx.unsis.unsiSmile.repository.patients;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProgressNoteRepository extends JpaRepository<ProgressNoteModel, String> {
    Page<ProgressNoteModel> findByPatient(PatientModel patient, Pageable pageable);
    // buscar por id de progress note
    Optional<ProgressNoteModel> findById(String id);
    
}