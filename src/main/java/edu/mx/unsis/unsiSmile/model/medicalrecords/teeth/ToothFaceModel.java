package edu.mx.unsis.unsiSmile.model.medicalrecords.teeth;

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
@Table(name = "tooth_faces")
public class ToothFaceModel extends AuditModel {

    @Id
    @Column(name = "id_tooth_face", length = 3)
    private String idToothFace;

    @Column(name = "description", nullable = false)
    private String description;
}