package edu.mx.unsis.unsiSmile.repository.patients;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProgressNoteRepository extends JpaRepository<ProgressNoteModel, String> {

    Page<ProgressNoteModel> findByPatient(PatientModel patient, Pageable pageable);

    Optional<ProgressNoteModel> findById(String id);
}