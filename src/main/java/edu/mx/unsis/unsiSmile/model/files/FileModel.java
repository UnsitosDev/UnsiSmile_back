package edu.mx.unsis.unsiSmile.model.files;

import edu.mx.unsis.unsiSmile.model.forms.answers.AnswerModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class FileModel extends AuditModel {

    @Id
    @Column(name = "id_file ", unique = true, nullable = false)
    private String idFile;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_answer")
    private AnswerModel answer;
}