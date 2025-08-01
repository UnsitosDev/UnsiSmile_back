package edu.mx.unsis.unsiSmile.model.patients;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "progress_note_files")
public class ProgressNoteFileModel extends AuditModel {
    @Id
    @Column(name = "id_progress_note_file", nullable = false, updatable = false, length = 36)
    private String idProgressNoteFile;

    @Column(name = "url", length = 255)
    private String url;

    @Column(name = "extension", length = 6)
    private String extension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_progress_note", nullable = false)
    private ProgressNoteModel progressNote;
}