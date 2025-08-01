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
@Table(name = "form_components")
public class FormComponentModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_form_component")
    private Long idFormComponent;

    @Column(name = "description", nullable = false, length = 255)
    private String description;
}