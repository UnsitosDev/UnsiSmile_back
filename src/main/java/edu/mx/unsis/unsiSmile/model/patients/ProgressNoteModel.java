package edu.mx.unsis.unsiSmile.model.patients;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "progress_notes")
public class ProgressNoteModel extends AuditModel {
    @Id
    @Column(name = "id_progress_note", nullable = false, updatable = false, length = 36)
    private String idProgressNote;

    @Column(name = "url", nullable = false, length = 255)
    private String url;

    @Column(name = "extention", nullable = false, length = 6)
    private String extention;

    @ManyToOne
    @JoinColumn(name = "fk_patient", nullable = false)
    private PatientModel patient;

    @PrePersist
    public void generateId() {
        if (idProgressNote == null) {
            idProgressNote = UUID.randomUUID().toString();
        }
    }
}