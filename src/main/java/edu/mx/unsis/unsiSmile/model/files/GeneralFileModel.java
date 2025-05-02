package edu.mx.unsis.unsiSmile.model.files;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "general_files")
public class GeneralFileModel extends AuditModel {

    @Id
    @Column(name = "id_file", unique = true, nullable = false)
    private String idFile;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "extension", nullable = false, length = 6)
    private String extension;
}