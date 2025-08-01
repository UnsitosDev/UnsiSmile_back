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
@Table(name = "toothface_conditions")
public class ToothFaceConditionModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_toothface_conditions")
    private Long idToothFaceCondition;

    @Column(name = "description", length = 50, nullable = false)
    private String description;
}