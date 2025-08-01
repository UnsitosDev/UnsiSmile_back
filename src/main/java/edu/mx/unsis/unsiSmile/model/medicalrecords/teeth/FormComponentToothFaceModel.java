package edu.mx.unsis.unsiSmile.model.medicalrecords.teeth;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "form_component_tooth_faces")
public class FormComponentToothFaceModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_form_component_tooth_face")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_form_component", nullable = false)
    private FormComponentModel formComponent;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_face", nullable = false)
    private ToothFaceConditionModel toothFace;
}
