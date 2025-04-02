package edu.mx.unsis.unsiSmile.model.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
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